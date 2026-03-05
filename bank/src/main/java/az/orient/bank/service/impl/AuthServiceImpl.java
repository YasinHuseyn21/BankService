package az.orient.bank.service.impl;

import az.orient.bank.config.JwtUtil;
import az.orient.bank.dto.request.ReqAuth;
import az.orient.bank.dto.response.RespAuth;
import az.orient.bank.dto.response.RespStatus;
import az.orient.bank.dto.response.Response;
import az.orient.bank.entity.Auth;
import az.orient.bank.exception.BankException;
import az.orient.bank.exception.ExceptionConstants;
import az.orient.bank.repository.AuthRepository;
import az.orient.bank.service.AuthService;
import az.orient.bank.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    public final AuthRepository authRepository;
    public final Utility utility;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtutil;
    private final PasswordEncoder bCryptPasswordEncoder;


    @Override
    public Response loginSpring(ReqAuth reqAuth) {
        Response response = new Response();
        RespAuth respAuth = new RespAuth();
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(reqAuth.username(), reqAuth.password());
            Authentication auth = authenticationManager.authenticate(token);

            String jwt = jwtutil.generateToken(reqAuth.username());
            long expires = jwtutil.getExpirationDateFromToken(jwt).getTime();

            respAuth.setToken(jwt);
            respAuth.setExpiresAt(expires);

            response.setT(respAuth);
            response.setStatus(RespStatus.success());
        } catch (BankException e) {
            response.setStatus(new RespStatus(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Server Error"));
        }
        return response;
    }

//    @Override
//    public Response login(ReqAuth reqAuth) {
//        Response response = new Response();
//        RespAuth respAuth = new RespAuth();
//        try {
//            if (reqAuth == null) {
//                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request Data");
//            }
//            Auth auth = authRepository.findAuthByUsernameAndPasswordAndActive(
//                    reqAuth.username(),
//                    reqAuth.password(),
//                    EnumStatus.ACTIVE.getValue());
//            System.out.println(auth);
//            String token = auth.getToken();
//            if (auth == null) {
//                throw new BankException(ExceptionConstants.USER_OR_PASSWORD_NOT_CORRECT, "Username or password incorrect");
//            }
//            if (token != null) {
//                throw new BankException(ExceptionConstants.SESSION_ALREADY_EXIST, "Session already exist");
//            }
//            auth.setToken(UUID.randomUUID().toString());
//            authRepository.save(auth);
//            respAuth.setToken(auth.getToken());
//            respAuth.setUsername(auth.getUsername());
//            response.setT(respAuth);
//            response.setStatus(RespStatus.success());
//        } catch (BankException e) {
//            e.printStackTrace();
//            response.setStatus(new RespStatus(e.getCode(), e.getMessage()));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
//        }
//        return response;
//    }

    @Override
    public Response logout(String token, String username) {
        Response response = new Response();
        try {
            Auth auth = utility.checkToken(token, username);
            auth.setToken(null);
            authRepository.save(auth);
            response.setStatus(RespStatus.success());
        } catch (BankException e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(e.getCode(), e.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }

        return response;
    }

    @Override
    public Response registration(ReqAuth reqAuth) {
        Response response = new Response();
        try {
            if (reqAuth == null) {
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid Request Data");
            }
            Auth auth = new Auth();
            auth.setUsername(reqAuth.username());
            auth.setPassword(bCryptPasswordEncoder.encode(reqAuth.password()));
            Optional<Auth> authOptional = authRepository.findByUsername(auth.getUsername());
            if (authOptional.isPresent()) {
                throw new BankException(ExceptionConstants.USER_ALREADY_EXIST, "User Already Exist");
            }
            authRepository.save(auth);

            response.setStatus(RespStatus.success());

        } catch (BankException e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Server Error"));
        }


        return response;
    }
}
