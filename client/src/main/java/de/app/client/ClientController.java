package de.app.client;

import de.app.client.DTOs.DashboardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientController {

    @Value("${app.shortener.public-url}")
    private String publicShortenerUrl;

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ModelAttribute("baseUrl")
    public String getBaseUrl() {
        return publicShortenerUrl;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/getNewToken")
    public String getNewToken(RedirectAttributes redirectAttributes){
        try {
            String token = clientService.takeNewTokenFromWorkspace();

            redirectAttributes.addFlashAttribute("generateTokenSuccess", "Dein neuer Workspace wurde erstellt!");
            redirectAttributes.addFlashAttribute("newToken", token);

        } catch (Exception e) {
            log.error("Absturz in getNewToken verhindert: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("generateTokenFailure", "Workspace konnte nicht erstellt werden. Service ist offline.");
        }
        return "redirect:/";
    }

    @PostMapping("/generateLink")
    public String createNewLink(
            @RequestParam String originalUrl,
            @RequestParam(required = false) String workspaceId,
            RedirectAttributes redirectAttributes) {

        try {
            String shortUrl = clientService.takeNewShortLinkFromShortener(originalUrl, workspaceId);
            redirectAttributes.addFlashAttribute("shortUrl", publicShortenerUrl + shortUrl);
            redirectAttributes.addFlashAttribute("urlSuccess", "Link erfolgreich gekürzt!");

            if (workspaceId != null && !workspaceId.isEmpty()) {
                redirectAttributes.addFlashAttribute("eingetrageneWorkspaceId", workspaceId);
            }

        } catch (IllegalArgumentException e) {
            log.warn("Link-Generierung abgelehnt: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("urlFailure", e.getMessage()); // Zeigt "Die angegebene Workspace-ID existiert nicht!"
            redirectAttributes.addFlashAttribute("eingetrageneOriginalUrl", originalUrl);

        } catch (Exception e) {
            log.error("Absturz in createNewLink verhindert: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("urlFailure", "Link konnte nicht gekürzt werden. Service offline.");
            redirectAttributes.addFlashAttribute("eingetrageneOriginalUrl", originalUrl);
        }

        return "redirect:/";
    }

    @PostMapping("/dashboard")
    public String showDashboardPage(@RequestParam String token, Model model, RedirectAttributes redirectAttributes){
        try {
            if (clientService.tokenExists(token)){
                DashboardDTO dashboardDTO = clientService.getDashboard(token);
                model.addAttribute("Dashboard", dashboardDTO);
                return "dashboard";
            } else {
                log.warn("Login-Versuch mit falschem Token: {}", token);
                redirectAttributes.addFlashAttribute("dashboardTokenError", "Dieser Workspace-Token ist ungültig!");
                return "redirect:/";
            }
        } catch (Exception e) {
            log.error("Absturz in showDashboardPage verhindert: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("dashboardTokenError", "Das Dashboard kann aktuell nicht geladen werden. Service offline.");
            return "redirect:/";
        }
    }
}