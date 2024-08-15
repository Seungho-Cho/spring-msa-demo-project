package com.example.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Controller
public class UiController {

    private final WebClient webClient;

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    public UiController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping("/inventory")
    public String getInventoryList(Model model) {
        List<Object> inventoryList = webClient.get()
                .uri(inventoryServiceUrl + "/api/inventory")
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .block();

        model.addAttribute("inventoryList", inventoryList);
        return "inventory";
    }

    @GetMapping("/inventory/add")
    public String showAddInventoryForm(Model model) {
        model.addAttribute("inventory", new InventoryForm());
        return "addInventory";
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
            return "addInventory";
        }
    }

    // Inventory Edit Form View
    @GetMapping("/inventory/edit/{id}")
    public String showEditInventoryForm(@PathVariable("id") Long id, Model model) {
        try {
            InventoryForm inventory = webClient.get()
                    .uri(inventoryServiceUrl + "/api/inventory/" + id)
                    .retrieve()
                    .bodyToMono(InventoryForm.class)
                    .block();
            model.addAttribute("inventory", inventory);
            return "editInventory";
        } catch (WebClientResponseException e) {
            model.addAttribute("error", "Failed to fetch inventory item: " + e.getMessage());
            return "inventory";
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
            return "editInventory";
        }
    }
}
