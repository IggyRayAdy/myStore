package com.example.myStore.Controller;

import com.example.myStore.Controller.Utils.ControllerUtils;
import com.example.myStore.Domain.*;
import com.example.myStore.Repository.CartRepository;
import com.example.myStore.Repository.OrderRepository;
import com.example.myStore.Repository.ProdRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("product/{id}")
public class ProdController {

    @Autowired
    private ProdRepository prodRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Value("${upload.path}")
    public String uploadPath;

    @GetMapping
    public String getProduct(
            @PathVariable("id") Product product,
            Model model
    ) {
        model.addAttribute("product", product);
        return "productPage";
    }

    /*@PostMapping
    public String updateOrder(
            @PathVariable("id") Product productDB,
            @RequestParam int cost,
            @RequestParam int discount,
            @RequestParam String label,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file
//            ,
//            @RequestParam Map<String, String> form,
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
                BeanUtils.copyProperties(product, productDB, "id", "typeOfProduct");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BeanUtils.copyProperties(product, productDB, "id", "typeOfProduct", "fileName");
        prodRepository.save(productDB);
        return "redirect:/product/{id}";
    }*/

    @PostMapping("/toCartProdPage")
    public String toCart(
            @PathVariable("id") Product product
    ) {
        saveOrder(product);
        return "redirect:/product/{id}";
    }

    @PostMapping("/delete")
    public String delete(
            @PathVariable("id") Product productDB
    ) {
        prodRepository.delete(productDB);
        return "redirect:/";
    }

    public void saveOrder(Product product) {
//      public void saveOrder(Optional<Product> optional) {
//      Product product = optional.get();
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
    public String updateOrder(

            @PathVariable("id") Product productDB,
            @RequestParam Map<String, String> form,
            @RequestParam("file") MultipartFile file,
            @Valid Product product,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAllAttributes(errors);

            /*TypeOfProduct[] types = TypeOfProduct.values();
            model.addAttribute("types", types);*/

            model.addAttribute("product", productDB);
            return "productPage";
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
                BeanUtils.copyProperties(product, productDB, "id", "typeOfProduct"/*, "order"*/);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BeanUtils.copyProperties(product, productDB, "id", "typeOfProduct", "fileName"/*, "order"*/);
        prodRepository.save(productDB);
        return "redirect:/product/{id}";
    }

}



