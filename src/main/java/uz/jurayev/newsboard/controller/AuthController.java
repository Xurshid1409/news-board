package uz.jurayev.newsboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.jurayev.newsboard.data.User;
import uz.jurayev.newsboard.model.MessageResponse;
import uz.jurayev.newsboard.model.request.RegisterRequest;
import uz.jurayev.newsboard.service.UserService;
import uz.jurayev.newsboard.util.ResponseErrorValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final ResponseErrorValidation errorValidation;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    /*
        Authentication url /api/login configured SecurityConfig.class
     */


    @PostMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, null);
    }

    @PostMapping("signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterRequest request,
                                               BindingResult bindingResult) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        userService.createUser(request);
        return ResponseEntity.ok(new MessageResponse("User Registered successfully"));
    }


    /**
     * Spring mvc
     */

    @GetMapping("login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("register")
    public String getRegisterPage(Model model) {
        model.addAttribute("newUser", new User());
        return "register_user";
    }

    @GetMapping("index")
    public String getSuccessPage(@ModelAttribute("newUser") User user) {

        return "user_index";
    }

}
