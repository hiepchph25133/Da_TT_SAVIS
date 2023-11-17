package com.example.demo.controller.Security;
import com.example.demo.Security.JWTGenerator;
import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.repository.TaiKhoanRepository;
import com.example.demo.repository.VaiTroRepository;
import com.example.demo.service.impl.TaiKhoanServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private TaiKhoanRepository taiKhoanRepository;
    private VaiTroRepository vaiTroRepository;
    private PasswordEncoder passwordEncoder;

    private JWTGenerator jwtGenerator;

    @Autowired
    private TaiKhoanServiceImpl taiKhoanService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TaiKhoanRepository taiKhoanRepository,
                          VaiTroRepository vaiTroRepository, PasswordEncoder passwordEncoder,JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.taiKhoanRepository = taiKhoanRepository;
        this.vaiTroRepository = vaiTroRepository;
        this.passwordEncoder = passwordEncoder;
       this.jwtGenerator = jwtGenerator;
    }

//    @PostMapping("login")
//    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginDto.getUserName(),
//                        loginDto.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtGenerator.generateToken(authentication);
//        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
//    }

    @PostMapping("login")
    public String login(@RequestBody LoginDTO loginDto, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUserName(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        // Thực hiện chuyển hướng từ API Controller
        return "forward:/login-success";
    }






    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute RegisterDTO registerDTO) {
        // Xử lý đăng ký tài khoản ở đây
        TaiKhoan taiKhoan = taiKhoanService.createTaiKhoan(registerDTO);

        // Thêm thông báo thành công vào cơ thể (body) của ResponseEntity
        String successMessage = "User registered successfully!";

        // Trả về ResponseEntity với thông báo và mã trạng thái OK
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

}
