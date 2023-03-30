package forexbet.tradingforecasts.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

public class IpBlackListInterceptor implements HandlerInterceptor {
    private final List<String> blacklistedIpAddresses = new ArrayList<>();

    public IpBlackListInterceptor() {
        blacklistedIpAddresses.add("102.128.192.0");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        System.out.println(ipAddress);
        if(blacklistedIpAddresses.contains(ipAddress)) {
            response.sendRedirect("/error");
        }
        return true;
    }
}
