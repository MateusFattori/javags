package br.com.fiap.gsdevops.auth;

import br.com.fiap.gsdevops.model.Usuario;
import br.com.fiap.gsdevops.service.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final UsuarioService usuarioService;

    public AuthorizationFilter(AuthenticationManager authenticationManager, UsuarioService usuarioService) {
        super(authenticationManager);
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String email = request.getParameter("email");

        if (email != null) {
            Optional<Usuario> usuarioOptional = usuarioService.findByEmail(email);

            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                String emailUsuario = usuario.getEmail();
                String senhaUsuario = usuario.getSenha();

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        emailUsuario, senhaUsuario, null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }
}
