    package ru.relex.jakartaee_project.filter;

    import jakarta.servlet.*;
    import jakarta.servlet.annotation.WebFilter;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;

    import java.io.IOException;
    import java.util.Set;

    @WebFilter("/*")
    public class AuthorizationFilter implements Filter {

        private static final Set<String> PUBLIC_PATHS = Set.of(
                "/",
                "/index.jsp",
                "/login",
                "/registration",
                "/css",          // если есть статика
                "/style",        // CSS files directory
                "/js",
                "/images"
        );

        @Override
        public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
                throws IOException, ServletException {

            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;

            String uri = request.getRequestURI();

            if (isPublicPath(uri) || isUserLoggedIn(request)) {
                chain.doFilter(req, resp);
            } else {
                response.sendRedirect("/login");
            }
        }

        private boolean isUserLoggedIn(HttpServletRequest request) {
            return request.getSession().getAttribute("user") != null;
        }

        private boolean isPublicPath(String uri) {
            if (uri.equals("/") || uri.equals("/index.jsp")) {
                return true;
            }
            // Check for paths that start with public prefixes (excluding root)
            return uri.startsWith("/login") ||
                    uri.startsWith("/registration") ||
                    uri.startsWith("/css") ||
                    uri.startsWith("/style") ||
                    uri.startsWith("/js") ||
                    uri.startsWith("/images");
        }
    }
