package com.example.springsecurityapplication.controllers.user;

import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Cart;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CartRepository;
import com.example.springsecurityapplication.repositories.OrderRepository;
import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.security.PersonDetails;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public UserController(ProductRepository productRepository, ProductService productService,CartRepository cartRepository, OrderRepository orderRepository) {
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    @GetMapping("/index")
    public String index(Model model) {

        // Получаем объект аутентификации - > с помощью Spring SecurityContextHolder обращаемся к контексту и на нем вызываем метод аутентификации.
        // Из потока для текущего пользователя мы получаем объект, который был положен в сессию после аутентификации
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
//        System.out.println("ID пользователя: " + personDetails.getPerson().getId());
//        System.out.println("Логин пользователя: " + personDetails.getPerson().getLogin());
//        System.out.println("Пароль пользователя: " + personDetails.getPerson().getPassword());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        //Получаем роль пользователя:
        String role = personDetails.getPerson().getRole();
        // через equals прописываем переход
        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/admin";
        }
        model.addAttribute("product", productService.getAllProducts());
        return "user/index";
    }
//    @GetMapping("/check")
//    public String check(Model model, BindingResult bindingResult) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
//        //Получаем роль пользователя:
//        String role = personDetails.getPerson().getRole();
//        // через equals прописываем переход
//        if (role.equals("ROLE_ADMIN")) {
//            return "redirect:/admin";
//        }
//        if (role.equals("ROLE_USER")) {
//            model.addAttribute("product", productService.getAllProducts());
//            return "user/index";
//        } else
//        if (bindingResult.hasErrors()){
//            return "product/product";
//        }
//        return "product/product";
//
//    }
    @GetMapping("/info/{id}")
    public String infoUser(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "user/infoProduct";
    }


    @GetMapping("/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int id, Model model){
        //создаем объект товара и помещаем ему объект товара по айди
        // 02:34:00 /29/11 получаем айдишник пользователя через аутентификацию
        Product product = productService.getProductId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        // создаем новый объект корзины и сохраняем, указав в параметрах выдернутые айди товара и пользователя
        Cart cart = new Cart(id_person, product.getId());
        cartRepository.save(cart);
        //эта страничка будет отвечать за вывод товара
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model){
        // как только пользователь зайдет в свою корзину, мы должны из корзины подгрузить данные
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        // Это мы взяли из сессии айди пользователя и по нему берем список товаров, которые были по данному пользователю
        List<Cart> cartList = cartRepository.findByPersonId(id_person);
        //получаем все продукты
        List<Product> productsList = new ArrayList<>();
        //пробежимся for each по картлисту
        for (Cart cart: cartList) {
            // мы пробегаемся по всем корзинам и добавляем элемент в лист продуктов
            productsList.add(productService.getProductId(cart.getProductId()));
        }

        float price = 0;
        for (Product product: productsList) {
            price += product.getPrice();
        }
        // в модель добавляем лист корзины и возвращаем страниччку с корзиной
        model.addAttribute("price", price);
        model.addAttribute("cart_product", productsList);
        return "user/cart";
    }
    @GetMapping("/cart/delete/{id}")
    public String deleteProductCart(Model model, @PathVariable("id") int id){
        //02:44:00 29/11 Во-первых нужно получить объект пользователя, который хочет удалить
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        // в репозитории создали метод по удалению по айдишнику, обращаемся к нему:
        cartRepository.deleteCartByProductId(id);
        return "redirect:/cart";
    }

    @GetMapping("/order/create")
    public String order(){
        //из сессии получаем айди пользователя чтобы понимать какой пользователь делает заказ, подгружаем всю корзину пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Cart> cartList = cartRepository.findByPersonId(id_person);
        List<Product> productsList = new ArrayList<>();
        // Получаем продукты из корзины по id
        for (Cart cart: cartList) {
            productsList.add(productService.getProductId(cart.getProductId()));
        }
        // считаем итоговую цену заказа, перебираем продукты и ищем итоговую цену по заказу
        float price = 0;
        for (Product product: productsList){
            price += product.getPrice();
        }
        // работа с номером заказа: используем uuid для файлов, который уникальны и это будет номер нашего заказа.
        String uuid = UUID.randomUUID().toString();
        //пробегаемся по всем продуктам
        for (Product product: productsList){
            // создаем объект заказа,
            Order newOrder = new Order(uuid, product, personDetails.getPerson(), 1, product.getPrice(), Status.Получен);
            // сохраняем объект заказа
            orderRepository.save(newOrder);
            //раз продукт перешел в заказ, удалем этот продукт из корзины
            cartRepository.deleteCartByProductId(product.getId());
        }
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String ordersUser(Model model){
        //подтягиваем из сессии объект пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        //получаем все заказы пользователя
        List<Order> orderList = orderRepository.findByPerson(personDetails.getPerson());
        // лист заказов помещаем в модель
        model.addAttribute("orders", orderList);
        return "/user/orders";
    }

    @PostMapping("/search")
    public String productSearch(@RequestParam("search") String search, @RequestParam("ot") String ot, @RequestParam("do") String Do, @RequestParam(value = "price", required = false, defaultValue = "")String price, @RequestParam(value = "contact", required = false, defaultValue = "")String contact, Model model){
        if(!ot.isEmpty() & !Do.isEmpty()){
            if(!price.isEmpty()){
                if(price.equals("sorted_by_ascending_price")){
                    if(!contact.isEmpty())
                    {
                        if(contact.equals("furniture")){
                            //toLowerCase преобразует в нижний регистр,Float.parseFloat переконвертация типов, в методе мы прописали стринг, а по модели это float значение, у декоративной мебели id 2 в таблице категорий.
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        } else if(contact.equals("lighting")){
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                        }else if(contact.equals("kitchen_sets")){
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        }
                    }
                }
                else if (price.equals("sorted_by_descending_price")){
                    if(!contact.isEmpty())
                    {
                        if(contact.equals("furniture")){
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        } else if(contact.equals("lighting")){
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                        }else if(contact.equals("kitchen_sets")){
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        }
                    }
                }
            } else {
                model.addAttribute("search_product", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(search, Float.parseFloat(ot), Float.parseFloat(Do)));

            }
        }
        else {
            model.addAttribute("search_product", productRepository.findByTitleContainingIgnoreCase(search));
        }
        // сталкиваемся с проблемой, что если вывести информацию на этой же странице, то поля поиска будут очищены, чтобы они очищены не были, сделаем так, чтобы все это можно было вставить при вовзращении шаблона
        model.addAttribute("value_search", search);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", Do);
        model.addAttribute("product", productService.getAllProducts());
        return "/user/index";

    }
}