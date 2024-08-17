package com.csh.ui;

import com.csh.ui.form.UserForm;
import com.csh.ui.form.InventoryForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
//import util.JwtUtil;

import java.util.List;

@Controller
public class UiController {

    //private final RestTemplate restTemplate;
    private final WebClient webClient;

    //@Autowired
    //private JwtUtil jwtUtil;

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public UiController(WebClient.Builder webClientBuilder/*, RestTemplate restTemplate*/) {
        this.webClient = webClientBuilder.build();
        //this.restTemplate = restTemplate;
    }


    /*
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("userServiceUrl", userServiceUrl);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password, Model model) {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("userId", userId);
        loginRequest.put("password", password);

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Create the HTTP request
        HttpEntity<Map<String, String>> request = new HttpEntity<>(loginRequest, headers);

        // Send request to the user service to authenticate and get JWT
        try {
            String tokenUrl = UriComponentsBuilder.fromHttpUrl(userServiceUrl)
                    .path("/auth/login")
                    .toUriString();

            String token = restTemplate.postForObject(tokenUrl, request, String.class);

            // Store the token in the session or send it to the client as needed
            model.addAttribute("token", token);
            return "redirect:/"; // Or any page after successful login

        } catch (Exception e) {
            // Handle error (e.g., invalid credentials)
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
    */

    @GetMapping("/inventory")
    public String getInventoryList(Model model) {
        List<Object> inventoryList = webClient.get()
                .uri(inventoryServiceUrl + "/api/inventory")
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .block();

        model.addAttribute("inventoryList", inventoryList);
        return "inventory/inventory";
    }

    @GetMapping("/inventory/add")
    public String addInventoryForm(Model model) {
        model.addAttribute("inventory", new InventoryForm());
        return "inventory/addInventory";
    }

    // Handle Form Submission
    @PostMapping("/inventory/add")
    public String addInventory(@ModelAttribute InventoryForm inventoryForm, Model model) {
        try {
            webClient.post()
                    .uri(inventoryServiceUrl + "/api/inventory")
                    .bodyValue(inventoryForm)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return "redirect:/inventory";
        } catch (WebClientResponseException e) {
            model.addAttribute("error", "Failed to add inventory item: " + e.getMessage());
            return "inventory/addInventory";
        }
    }

    // Inventory Edit Form View
    @GetMapping("/inventory/edit/{id}")
    public String editInventoryForm(@PathVariable("id") Long id, Model model) {
        try {
            InventoryForm inventory = webClient.get()
                    .uri(inventoryServiceUrl + "/api/inventory/" + id)
                    .retrieve()
                    .bodyToMono(InventoryForm.class)
                    .block();
            model.addAttribute("inventory", inventory);
            return "inventory/editInventory";
        } catch (WebClientResponseException e) {
            model.addAttribute("error", "Failed to fetch inventory item: " + e.getMessage());
            return "inventory/inventory";
        }
    }

    // Handle Edit Form Submission
    @PostMapping("/inventory/edit/{id}")
    public String editInventory(@PathVariable("id") Long id, @ModelAttribute InventoryForm inventoryForm, Model model) {
        try {
            webClient.put()
                    .uri(inventoryServiceUrl + "/api/inventory/" + id)
                    .bodyValue(inventoryForm)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return "redirect:/inventory";
        } catch (WebClientResponseException e) {
            model.addAttribute("error", "Failed to update inventory item: " + e.getMessage());
            return "inventory/editInventory";
        }
    }

    @PostMapping("/inventory/delete/{id}")
    public String deleteInventory(@PathVariable("id") Long id, Model model) {
        try {
            webClient.delete()
                    .uri(inventoryServiceUrl + "/api/inventory/" + id)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return "redirect:/inventory";
        } catch (WebClientResponseException e) {
            model.addAttribute("error", "Failed to delete inventory item: " + e.getMessage());
            return "inventory/editInventory";
        }
    }

    @GetMapping("/inventory/search")
    public String searchInventories(Model model) {
        model.addAttribute("inventoryServiceUrl", inventoryServiceUrl);
        return "inventory/searchInventory";
    }

    // Handle Form Submission
    @GetMapping("/user/register")
    public String registerUserForm(@ModelAttribute UserForm userForm, Model model) {
        model.addAttribute("user", new UserForm());
        return "user/registerUser";
    }

    // Handle Form Submission
    @PostMapping("/user/register")
    public String registerUser(@ModelAttribute UserForm userForm, Model model) {
        try {
            webClient.post()
                    .uri(userServiceUrl + "/api/user")
                    .bodyValue(userForm)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return "redirect:/user";
        } catch (WebClientResponseException e) {
            model.addAttribute("error", "Failed to register user: " + e.getMessage());
            return "user/registerUser";
        }
    }

    @GetMapping("/user")
    public String getUserList(Model model) {
        List<Object> userList = webClient.get()
                .uri(userServiceUrl + "/api/user")
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .block();

        model.addAttribute("userList", userList);
        return "user/user";
    }
}
