package com.example.myStore.Controller;

import com.example.myStore.Controller.Utils.ControllerUtils;
import com.example.myStore.Domain.*;
import com.example.myStore.Repository.CartRepository;
import com.example.myStore.Repository.OrderRepository;
import com.example.myStore.Repository.ProdRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private ProdRepository prodRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping
    public String show(
            Model model
    ) {
        TypeOfProduct[] types = TypeOfProduct.values();
        Iterable<Product> products = prodRepository.findAll();
        model.addAttribute("types", types);
        model.addAttribute("products", products);
        return "mainPage";
    }

    @PostMapping("/toCartMainPage")
    public String toCart(
            @RequestParam Map<String, String> form
    ) {
        for (String key : form.keySet()) {
            if (key.contains("id")) {
                Optional<Product> optional = prodRepository.findById(Long.valueOf(form.get("id")));
                if (optional.isPresent()) {
//                    saveOrder(optional);
                    saveOrder(optional.get());
                }
            }
        }
        return "redirect:/";
    }

    /*@PostMapping
    public String addNewProd(
            @RequestParam int cost,
            @RequestParam int discount,
            @RequestParam String label,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam Map<String, String> form
    ) {
        Product product = new Product(cost, discount, label, description);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFileName = UUID.randomUUID().toString();
            try {
                file.transferTo(new File(uploadPath + "/" + uuidFileName));
                product.setFileName(uuidFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Set<String> typesProd = Arrays.stream(TypeOfProduct.values())
                .map(TypeOfProduct::name)
                .collect(Collectors.toSet());
        for (String key : form.keySet()) {
            if (typesProd.contains(key)) {
                product.setTypeOfProduct(TypeOfProduct.valueOf(key));
            }
        }
        prodRepository.save(product);
        return "redirect:/";
    }*/

    public void saveOrder(Product product) {
//        public void saveOrder (Optional < Product > optional) {
//        Product product = optional.get();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = cartRepository.findById(user.getCart().getId()).get();
        Order order = cart.getActualOrder();

        Long count = 1L;
        if (order == null) {
            order = new Order(cart);
            cart.setOrdersList(Arrays.asList(order));
            order.getOrderMap().put(product.getId(), count);
        } else {
            if (order.getOrderMap().containsKey(product.getId())) {
                Long currentCount = order.getOrderMap().get(product.getId());
                order.getOrderMap().put(product.getId(), ++currentCount);
            } else {
                order.getOrderMap().put(product.getId(), count);
            }
        }
        orderRepository.save(order);
    }

    @PostMapping
    public String addNewProd(
            @RequestParam Map<String, String> form,
            @RequestParam("file") MultipartFile file,
            @Valid Product product,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAllAttributes(errors);

            TypeOfProduct[] types = TypeOfProduct.values();
            Iterable<Product> products = prodRepository.findAll();
            model.addAttribute("types", types);
            model.addAttribute("products", products);
            return "mainPage";
        }

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFileName = UUID.randomUUID().toString();
            try {
                file.transferTo(new File(uploadPath + "/" + uuidFileName));
                product.setFileName(uuidFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Set<String> typesProd = Arrays.stream(TypeOfProduct.values())
                .map(TypeOfProduct::name)
                .collect(Collectors.toSet());
        for (String key : form.keySet()) {
            if (typesProd.contains(key)) {
                product.setTypeOfProduct(TypeOfProduct.valueOf(key));
            }
        }
        prodRepository.save(product);
        return "redirect:/";
    }

    @PostMapping("/searchByLabel")
    public String search(
            Model model,
            @RequestParam("searchTag") String searchTag
    ) {
        TypeOfProduct[] types = TypeOfProduct.values();
        model.addAttribute("types", types);

        if (searchTag.length() > 1) {
            List<Product> products = prodRepository.findAllByLabel(searchTag);
            if (products.size() <= 0) {
                model.addAttribute("message", "not found this label");
                return "mainPage";
            }
            model.addAttribute("products", products);
            return "mainPage";
        } else {
            return "redirect:/";
        }
    }

}

