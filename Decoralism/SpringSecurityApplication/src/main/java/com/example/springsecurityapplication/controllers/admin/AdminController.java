package com.example.springsecurityapplication.controllers.admin;

import com.example.springsecurityapplication.enumm.Role;
import com.example.springsecurityapplication.models.*;
import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.repositories.OrderRepository;
import com.example.springsecurityapplication.services.OrderService;
import com.example.springsecurityapplication.services.PersonService;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
// 00^47^00 24/11 Укажем что данный контроллер будет работать если в начале нашего запроса будет /admin и уберем с другого метода admin чтобы не было конфликтов
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class AdminController {

    @Value("${upload.path}")
    private String uploadPath;

    private final ProductService productService;

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;

    private final PersonService personService;

    @Autowired
    public AdminController(ProductService productService, CategoryRepository categoryRepository,OrderService orderService, OrderRepository orderRepository,PersonService personService) {
        this.productService = productService;
        this.categoryRepository=categoryRepository;
        this.orderService=orderService;
        this.orderRepository=orderRepository;
        this.personService=personService;
    }



//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("")
    public String admin(Model model){
        model.addAttribute("products", productService.getAllProducts());//закидываем объект всех товаров в модель и там на главной странице будет работать с этими продуктами
        return "admin/admin";
    }

    // http:/localhost:8080/admin/product/add
    //метод по отображению страницы с возможностью добавления товара
    //создаем атрибут категории в модели и погружаем в него все данные по ним
    @GetMapping("/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }
    //добавление товара через сервис продуктрепозиторий
    //MultipartFile позволяем получить файл с формы
    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product")@Valid Product product, BindingResult bindingResult, @RequestParam ("file_one")MultipartFile file_one, @RequestParam ("file_two")MultipartFile file_two, @RequestParam ("file_three")MultipartFile file_three, @RequestParam ("file_four")MultipartFile file_four, @RequestParam ("file_five")MultipartFile file_five) throws IOException {
        if (bindingResult.hasErrors()){
            return "product/addproduct";
        }
        if(file_one != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            // Получение уникального имени
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            // сохраняем путь по нашему пути
            file_one.transferTo(new File(uploadPath+ "/" + resultFileName));
            // создаем объект нашей модели и привязываем продукт к данной фотографии
            Image image = new Image();
            image.setProduct(product);
            // указываем наименование файла в папке
            image.setFileName(resultFileName);
            // пропишем логику сохранения нашей фотографии
            product.addImageToProduct(image);
        }
        if(file_five != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            // Получение уникального имени
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
            // сохраняем путь по нашему пути
            file_five.transferTo(new File(uploadPath+ "/" + resultFileName));
            // создаем объект нашей модели и привязываем продукт к данной фотографии
            Image image = new Image();
            image.setProduct(product);
            // указываем наименование файла в папке
            image.setFileName(resultFileName);
            // пропишем логику сохранения нашей фотографии
            product.addImageToProduct(image);
        }
        if(file_two != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            // Получение уникального имени
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            // сохраняем путь по нашему пути
            file_two.transferTo(new File(uploadPath+ "/" + resultFileName));
            // создаем объект нашей модели и привязываем продукт к данной фотографии
            Image image = new Image();
            image.setProduct(product);
            // указываем наименование файла в папке
            image.setFileName(resultFileName);
            // пропишем логику сохранения нашей фотографии
            product.addImageToProduct(image);
        }
        if(file_three != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            // Получение уникального имени
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            // сохраняем путь по нашему пути
            file_three.transferTo(new File(uploadPath+ "/" + resultFileName));
            // создаем объект нашей модели и привязываем продукт к данной фотографии
            Image image = new Image();
            image.setProduct(product);
            // указываем наименование файла в папке
            image.setFileName(resultFileName);
            // пропишем логику сохранения нашей фотографии
            product.addImageToProduct(image);
        }
        if(file_four != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            // Получение уникального имени
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            // сохраняем путь по нашему пути
            file_four.transferTo(new File(uploadPath+ "/" + resultFileName));
            // создаем объект нашей модели и привязываем продукт к данной фотографии
            Image image = new Image();
            image.setProduct(product);
            // указываем наименование файла в папке
            image.setFileName(resultFileName);
            // пропишем логику сохранения нашей фотографии
            product.addImageToProduct(image);
        }
        productService.saveProduct(product);
        return "redirect:/admin";
    }
    @GetMapping("/product/delete{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }
    // метод по редактированию товара
    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }
    //метод по сохранению изменений товара
    @PostMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") int id, @ModelAttribute("product")@Valid Product product,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        System.out.println(id);
        return "redirect:/admin";
    }
    // получаем список всех заказов в админке
    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("orders", orderService.getAllOrders());//закидываем объект всех товаров в модель и там на главной странице будет работать с этими продуктами
        return "admin/orders";
    }
    @GetMapping("/order/edit/{id}")
    public String editOrderStatus(@PathVariable("id") int id, Model model){
        model.addAttribute("order", orderService.getOrderId(id));

        return "admin/editOrder";
    }
    @PostMapping("/order/edit/{id}")
    public String editOrderProduct(@PathVariable("id") int id, @ModelAttribute("status") Status status ){
        System.out.println(status);
        Order order = orderService.getOrderId(id);
        order.setStatus(status);
        orderService.updateOrder(order);
        //@RequestParam("status") Status status

        return "redirect:/admin";
    }
//
//    @PostMapping("order/search")
//    public String productSearch(@RequestParam("search") String search, Model model){
//        if(search == null){
//            model.addAttribute("error", "необходимо ввести 4 последние символа заказа");
//            model.addAttribute("orders", orderService.getAllOrders());
//            return "/admin/orders";
//        } else if(!(search.length() == 4)){
//            model.addAttribute("error", "необходимо ввести 4 последние символа заказа");
//            model.addAttribute("orders", orderService.getAllOrders());
//            return "/admin/orders";
//        } else {
//            System.out.println(search);
//                model.addAttribute("searchResults", orderService.findByLastNumberSymbols(search));
//
//            }
//
//        model.addAttribute("search", search);
//
//        model.addAttribute("orders", orderService.getAllOrders());
//        return "/admin/orders";
//
//    }

    // получаем список всех пользователей в админке
    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("users", personService.getAllPersons());//закидываем объект всех пользователей в модель и там на главной странице будет работать с этими пользователями
        return "admin/userList";
    }

    @GetMapping("/users/edit/{id}")
    public String editRoleUser(@PathVariable("id") int id, Model model){
        model.addAttribute("user", personService.getPersonId(id));

        return "admin/userEdit";
    }


    @PostMapping("/users/edit/{id}")
    public String editRoleUser(@PathVariable("id") int id, @ModelAttribute("role") String role) {

        System.out.println(role);
        Person person = personService.getPersonId(id);
        person.setRole(role);
        personService.updatePerson(id, person);
        //@RequestParam("status") Status status
        return "redirect:/admin/users";
    }


    // Поиск по четырем символам
    @PostMapping("/order/search")
    public String productSearch(@RequestParam("search") String search, Model model){
        if(search == null || !(search.length() == 4)){
            model.addAttribute("error", "необходимо ввести 4 последние символа заказа");
            model.addAttribute("orders", orderService.getAllOrders());
            return "/admin/orders";
        } else {
            List<Order> resultOrderList = new ArrayList<>();
            char[] chars = search.toCharArray();
            for(char s: chars){
                System.out.println(s);
            }
            List<Order> orderList = orderRepository.findAll();
            for(Order order: orderList){
                int k=0;
                System.out.println(order.getNumber());
                char[] charsOrder = order.getNumber().toCharArray();
                for(int i = 0; i < 4 ; i++){
                    System.out.println(chars[i]);
                    System.out.println(charsOrder[charsOrder.length - 4 + i]);
                    if(chars[i] == charsOrder[charsOrder.length - 4 + i]){
                        k++;
                        System.out.println("Нашли равные символы: " + chars[i]);
                    }
                }
                if(k == 4){
                    System.out.println("Нашли одного");
                    resultOrderList.add(order);
                }
            }

            model.addAttribute("ordersFounded", resultOrderList);
            model.addAttribute("orders", orderService.getAllOrders());
        }
        return "/admin/orders";
    }

}



