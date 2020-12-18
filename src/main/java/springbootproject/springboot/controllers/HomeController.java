package springbootproject.springboot.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springbootproject.springboot.entities.*;
import springbootproject.springboot.services.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ShopItemService shopItemService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShopitemOrderService shopitemOrderService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CommentService commentService;

    @Value("${file.avatar.viewPath}")
    private String viewPathAvatars;
    @Value("${file.avatar.uploadPath}")
    private String uploadPathAvatars;
    @Value("${file.avatar.defaultPicture}")
    private String defaultPicture;

    @Value("${file.shopitem.viewPath}")
    private String viewPathShopItems;
    @Value("${file.shopitem.uploadPath}")
    private String uploadPathShopItems;

    private void checkCartSession() {
        HashMap<ShopItem, Integer> cart = (HashMap<ShopItem, Integer>) session.getAttribute("cart");
        if (cart == null) {
            session.setAttribute("cart", new HashMap<ShopItem, Integer>());
            session.setAttribute("totalQuantity", 0);
            session.setAttribute("totalAmount", 0);
        } else {
            double totalAmount = 0;
            int totalQuantity = 0;
            for (Map.Entry<ShopItem, Integer> entry : cart.entrySet()) {
                totalQuantity += entry.getValue();
                totalAmount += entry.getKey().getPrice() * entry.getValue();
            }
            session.setAttribute("totalAmount", totalAmount);
            session.setAttribute("totalQuantity", totalQuantity);
        }
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        List<ShopItem> shopItems = shopItemService.getTopShopItems();
        model.addAttribute("shopItems", shopItems);

        List<Brand> brands = shopItemService.getBrands();
        model.addAttribute("brands", brands);

        List<Country> countries = brandService.getCountries();
        model.addAttribute("countries", countries);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("currentUser", getUserData());

        checkCartSession();

        return "index";
    }

    @GetMapping(value = "/search")
    public String search(Model model,
                        @RequestParam(name = "shopitem_name", defaultValue = "") String name,
                        @RequestParam(name = "brand_id", defaultValue = "0") int brandId,
                        @RequestParam(name = "category_id", defaultValue = "0") int category_id,
                        @RequestParam(name = "shopitem_fromprice", defaultValue = "0") int priceFrom,
                        @RequestParam(name = "shopitem_toprice", defaultValue = "2000000000") int priceTo,
                        @RequestParam(name = "shopitem_stars", defaultValue = "0") int stars,
                        @RequestParam(name = "order", defaultValue = "") String order
            ) {
        List<ShopItem> shopItems = shopItemService.getFilteredShopItems(name, brandId, category_id, priceFrom, priceTo, stars, order);
        model.addAttribute("shopItems", shopItems);

        List<Brand> brands = shopItemService.getBrands();
        model.addAttribute("brands", brands);

        List<Country> countries = brandService.getCountries();
        model.addAttribute("countries", countries);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("currentUser", getUserData());

        checkCartSession();

        return "search";
    }

    @GetMapping(value = "/details/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String details(Model model, @PathVariable(name = "id") int id) {
        ShopItem shopItem = shopItemService.getShopItem(id);
        model.addAttribute("shopitem", shopItem);

        List<Brand> brands = shopItemService.getBrands();
        model.addAttribute("brands", brands);

        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        List<Picture> pictures = pictureService.getPicturesByShopItem(shopItem);
        model.addAttribute("pictures", pictures);

        model.addAttribute("currentUser", getUserData());

        checkCartSession();

        return "details";
    }

    @GetMapping(value = "/properties/{id}")
    public String properties(Model model, @PathVariable(name = "id") int id) {
        ShopItem shopItem = shopItemService.getShopItem(id);
        model.addAttribute("shopitem", shopItem);

        List<Brand> brands = shopItemService.getBrands();
        model.addAttribute("brands", brands);

        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        List<Picture> pictures = pictureService.getPicturesByShopItem(shopItem);
        model.addAttribute("pictures", pictures);

        model.addAttribute("currentUser", getUserData());

        model.addAttribute("comments", commentService.getAllByShopItem(shopItem));

        checkCartSession();

        return "properties";
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String admin(Model model) {
        List<ShopItem> shopItems = shopItemService.getAllShopItems();
        model.addAttribute("shopItems", shopItems);

        List<Brand> brands = shopItemService.getBrands();
        model.addAttribute("brands", brands);

        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);

        model.addAttribute("currentUser", getUserData());

        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("shopitemOrders", shopitemOrderService.getAllShopitemOrders());

        checkCartSession();

        return "admin";
    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model) {
        List<Brand> brands = shopItemService.getBrands();
        model.addAttribute("brands", brands);

        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("currentUser", getUserData());

        checkCartSession();

        return "403";
    }

    @GetMapping(value = "/login")
    @PreAuthorize("isAnonymous()")
    public String login(Model model) {
        List<Brand> brands = shopItemService.getBrands();
        model.addAttribute("brands", brands);

        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("currentUser", getUserData());

        checkCartSession();

        return "login";
    }

    @GetMapping(value = "/signup")
    @PreAuthorize("isAnonymous()")
    public String signUp(Model model) {
        List<Brand> brands = shopItemService.getBrands();
        model.addAttribute("brands", brands);

        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("currentUser", getUserData());

        checkCartSession();

        return "signup";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        List<Brand> brands = shopItemService.getBrands();
        model.addAttribute("brands", brands);

        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("currentUser", getUserData());

        checkCartSession();

        return "profile";
    }

    @GetMapping(value = "/cart")
    public String cart(Model model) {
        List<Brand> brands = shopItemService.getBrands();
        model.addAttribute("brands", brands);

        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("currentUser", getUserData());

        checkCartSession();
        model.addAttribute("cart", session.getAttribute("cart"));

        return "cart";
    }

    @GetMapping(value = "/viewphoto/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
//    @PreAuthorize("isAuthenticated()")
    public @ResponseBody byte[] viewProfilePhoto(@PathVariable(name = "url") String url) throws IOException {
        String pictureUrl = viewPathAvatars + "/" + defaultPicture;
        if (url != null && !url.equals("null")) {
            pictureUrl = viewPathAvatars + "/" + url + ".jpg";
        }

        InputStream inputStream;
        try {
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            inputStream = resource.getInputStream();
        } catch (Exception e) {
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            inputStream = resource.getInputStream();
            e.printStackTrace();
        }
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping(value = "/shopitem/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] viewShopItemPhoto(@PathVariable(name = "url") String url) throws IOException {
        String pictureUrl;
        if (url != null && !url.equals("null")) {
            pictureUrl = viewPathShopItems + "/" + url + ".jpg";

            InputStream inputStream;
            try {
                ClassPathResource resource = new ClassPathResource(pictureUrl);
                inputStream = resource.getInputStream();
            } catch (Exception e) {
                ClassPathResource resource = new ClassPathResource(pictureUrl);
                inputStream = resource.getInputStream();
                e.printStackTrace();
            }
            return IOUtils.toByteArray(inputStream);
        }
        return null;
    }

    private User getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( !(authentication instanceof AnonymousAuthenticationToken) ) {
            org.springframework.security.core.userdetails.User secUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            return userService.getUserByEmail(secUser.getUsername());
        }
        return null;
    }







    @PostMapping(value = "/delete-comment")
    public String deleteComment(
            @RequestParam(name = "shopitem_id") int shopItemId,
            @RequestParam(name = "comment_id") int commentId
    ) {
        String redirect = "?error";
        Comment comment = commentService.getComment(commentId);
        if (comment != null) {
            commentService.deleteComment(comment);
            redirect = "#comments";
        }
        return "redirect:/properties/" + shopItemId + redirect;
    }

    @PostMapping(value = "/edit-comment")
    public String editComment(
            @RequestParam(name = "shopitem_id") int shopItemId,
            @RequestParam(name = "author_id") int authorId,
            @RequestParam(name = "comment") String commentText,
            @RequestParam(name = "comment_id") int commentId
    ) {
        String redirect = "?error";
        ShopItem shopItem = shopItemService.getShopItem(shopItemId);
        User author = userService.getUser(authorId);
        Comment comment = commentService.getComment(commentId);
        if (author != null && shopItem != null && comment != null) {
            comment.setComment(commentText);
            commentService.addComment(comment);
            redirect = "#comments";
        }
        return "redirect:/properties/" + shopItemId + redirect;
    }

    @PostMapping(value = "/add-comment")
    public String addComment(
            @RequestParam(name = "shopitem_id") int shopItemId,
            @RequestParam(name = "author_id") int authorId,
            @RequestParam(name = "comment") String commentText
    ) {
        String redirect = "?error";
        ShopItem shopItem = shopItemService.getShopItem(shopItemId);
        User author = userService.getUser(authorId);
        if (author != null && shopItem != null) {
            Comment comment = new Comment();
            comment.setComment(commentText);
            comment.setAuthor(author);
            comment.setShopItem(shopItem);
            commentService.addComment(comment);
            redirect = "#comments";
        }
        return "redirect:/properties/" + shopItemId + redirect;
    }

    @PostMapping(value = "/pay_order")
    public String payOrder(
            @RequestParam(name = "fullname") String fullName,
            @RequestParam(name = "card") String card,
            @RequestParam(name = "expiration_date") String expirationDate,
            @RequestParam(name = "cvv") String cvv
    ) {
        String redirect = "error";

        if (card != null && cvv != null && !card.equals("") && !cvv.equals("")) {
            Map<ShopItem, Integer> cart = (HashMap<ShopItem, Integer>) session.getAttribute("cart");
            Set<ShopitemOrder> shopItemsSet = new HashSet<>();
            Order order = new Order();
            order.setBuyerName(fullName);
            orderService.addOrder(order);
            for (Map.Entry<ShopItem, Integer> entry : cart.entrySet()) {
                if (entry.getValue() > 0) {
                    ShopitemOrder shopitemOrder = new ShopitemOrder();
                    shopitemOrder.setOrder(order);
                    shopitemOrder.setShopItem(entry.getKey());
                    shopitemOrder.setQuantity(entry.getValue());

                    shopItemsSet.add(shopitemOrder);
                    shopitemOrderService.addShopitemOrder(shopitemOrder);
                }
            }
            session.setAttribute("cart", new HashMap<ShopItem, Integer>());
            session.setAttribute("totalQuantity", 0);
            session.setAttribute("totalAmount", 0);
            redirect = "success";
        }

        return "redirect:/cart?" + redirect;
    }


    @PostMapping(value = "/delete-cart")
    public String deleteCart() {
        session.setAttribute("cart", new HashMap<ShopItem, Integer>());
        session.setAttribute("totalQuantity", 0);
        session.setAttribute("totalAmount", 0);

//        return "redirect:/cart?success";
        return "redirect:/cart";
    }

    @PostMapping(value = "/delete_item_from_cart")
    public String deleteShopItemFromCart(
            @RequestParam(name = "shopitem_id") int shopitemId
    ) {
        String redirect = "error";

        ShopItem shopItem = shopItemService.getShopItem(shopitemId);
        HashMap<ShopItem, Integer> cart = (HashMap<ShopItem, Integer>) session.getAttribute("cart");
        if (cart != null && shopItem != null) {
            for (Map.Entry<ShopItem, Integer> entry : cart.entrySet()) {
                if (entry.getKey().getId() == shopitemId) {
                    if (entry.getValue() > 0) {
                        entry.setValue(entry.getValue() - 1);
                        break;
                    }
                }
            }
            redirect = "success";
        }
        session.setAttribute("cart", cart);

//        return "redirect:/cart?" + redirect;
        return "redirect:/cart";
    }


    @PostMapping(value = "/add_item_to_cart")
    public String addShopItemToCart(
            @RequestParam(name = "shopitem_id") int shopitemId,
            @RequestParam(name = "cart_id", defaultValue = "0") String isCart,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String redirect = "error";
        if (isCart.equals("0")) {
            isCart = "properties/" + shopitemId;
        } else {
            isCart = "cart";
        }

        HashMap<ShopItem, Integer> cart = (HashMap<ShopItem, Integer>) session.getAttribute("cart");
        if (cart != null && cart.size() > 0) {
            boolean added = false;
            for (Map.Entry<ShopItem, Integer> entry : cart.entrySet()) {
                ShopItem keyShopItem = entry.getKey();
                if (keyShopItem.getId() == shopitemId) {
                    entry.setValue(entry.getValue() + 1);
                    added = true;
                    redirect = "success";
                    break;
                }
            }
            if (!added) {
                cart.put(shopItemService.getShopItem(shopitemId), 1);
            }
        } else {
            cart = new HashMap<>();
            cart.put(shopItemService.getShopItem(shopitemId), 1);
            redirect = "success";
        }
        session.setAttribute("cart", cart);


        double totalAmount = 0;
        int totalQuantity = 0;
        for (Map.Entry<ShopItem, Integer> entry : cart.entrySet()) {
            totalQuantity += entry.getValue();
            totalAmount += entry.getKey().getPrice() * entry.getValue();
        }
        session.setAttribute("totalAmount", totalAmount);
        session.setAttribute("totalQuantity", totalQuantity);


        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);
                break;
            }
        }

//        return "redirect:/" + isCart + "?" + redirect;
        return "redirect:/" + isCart;
    }


    @PostMapping(value = "/upload_shopitem_photo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String uploadShopItemPhoto(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "shopitem_photo") MultipartFile shopitem_photo
    ) {
        String redirect = "error";

        ShopItem shopItem = shopItemService.getShopItem(id);
        if (shopItem != null) {
            if (shopitem_photo.getContentType().equals("image/jpeg") || shopitem_photo.getContentType().equals("image/png")) {
                try {
                    Picture picture = new Picture(0, null, LocalDateTime.now(), shopItem);
                    String picName = DigestUtils.sha1Hex("photo_" + LocalDateTime.now() + shopItem.getId() + "_!Picture");
                    picture.setUrl(picName);

                    byte[] bytes = shopitem_photo.getBytes();
                    Path path = Paths.get(uploadPathShopItems + "/" + picName + ".jpg");
                    Files.write(path, bytes);
                    pictureService.addPicture(picture);
                    redirect = "success";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        assert shopItem != null;
        return "redirect:/details/" + shopItem.getId() + "?" + redirect;
    }

    @PostMapping(value = "/uploadavatar")
    @PreAuthorize("isAuthenticated()")
    public String uploadAvatar(
            @RequestParam(name = "user_avatar") MultipartFile user_avatar
    ) {
        String redirect = "error";

        if (user_avatar.getContentType().equals("image/jpeg") || user_avatar.getContentType().equals("image/png")) {
            try {
                User currentUser = getUserData();
                String picName = DigestUtils.sha1Hex("avatar_" + currentUser.getId() + "_!Picture");

                byte[] bytes = user_avatar.getBytes();
                Path path = Paths.get(uploadPathAvatars + "/" + picName + ".jpg");
                Files.write(path, bytes);
                currentUser.setPictureUrl(picName);
                userService.saveUser(currentUser);
                redirect = "success";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/profile?" + redirect;
    }

    @PostMapping(value = "/saveuserpassword")
    @PreAuthorize("isAuthenticated()")
    public String saveUserPassword(
            Model model,
            @RequestParam(name = "id") int id,
            @RequestParam(name = "user_oldpassword") String oldpassword,
            @RequestParam(name = "user_newpassword") String newpassword,
            @RequestParam(name = "user_repassword") String repassword
    ) {
        String redirect = "error";
        User user = userService.getUser(id);
        if ( user != null ) {
            String pass = passwordEncoder.encode(oldpassword);
//            if (pass.equals(user.getPassword()) && newpassword.equals(repassword)) {
            if (newpassword.equals(repassword)) {
                user.setPassword(newpassword);
                userService.saveUser(user);
                model.addAttribute("currentUser", getUserData());
                redirect = "success";
            }
        }
        return "redirect:/profile?" + redirect;
    }


    @PostMapping(value = "/saveuser")
    @PreAuthorize("isAuthenticated()")
    public String saveUser(
            Model model,
            @RequestParam(name = "id") int id,
            @RequestParam(name = "user_fullname") String fullName,
            @RequestParam(name = "user_email") String email
    ) {
        String redirect = "error";
        User user = userService.getUser(id);
        if (user != null) {
            user.setFullName(fullName);
            user.setEmail(email);
            userService.saveUser(user);
            model.addAttribute("currentUser", getUserData());
            redirect = "success";
        }
        return "redirect:/profile?" + redirect;
    }

    @PostMapping(value = "/saveuserroles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveUserRoles(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "user_fullname") String fullName,
            @RequestParam(name = "user_email") String email,
            @RequestParam(name = "user_password", required = false) String password,
            @RequestParam(name = "role1", defaultValue = "0") int userRoleId,
            @RequestParam(name = "role3", defaultValue = "0") int moderatorRoleId,
            @RequestParam(name = "role2", defaultValue = "0") int adminRoleId
    ) {
        String redirect = "error";
        User user = userService.getUser(id);
        if (user != null) {
            user.setFullName(fullName);
            if (password != null && !password.equals("")) {
                user.setPassword(password);
            }
            List<Role> roles = new ArrayList<>();
            if (adminRoleId > 0) {
                roles.add(roleService.getRole(adminRoleId));
            }
            if (moderatorRoleId > 0) {
                roles.add(roleService.getRole(moderatorRoleId));
            }
            if (userRoleId > 0) {
                roles.add(roleService.getRole(userRoleId));
            }
            user.setRoles(roles);
            userService.saveUser(user);
            redirect = "success";
        }

        return "redirect:/admin?" + redirect;
    }

    @PostMapping(value = "/signup")
    @PreAuthorize("isAnonymous()")
    public String signUp(
            @RequestParam(name = "user_fullname") String name,
            @RequestParam(name = "user_email") String email,
            @RequestParam(name = "user_password") String password,
            @RequestParam(name = "user_repassword") String repassword
    ) {

        User user = userService.getUserByEmail(email);
        String redirect = "signup?error";
        if (user == null && password.equals(repassword)) {
            List<Role> roles = new ArrayList<>(1);
            roles.add(roleService.getRoleByName("ROLE_USER"));

            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setFullName(name);
            newUser.setRoles(roles);
//            userService.addUser(new User(0, email, password, name, null, roles));
            userService.addUser(newUser);
            redirect = "login?success";
        }
        return "redirect:/" + redirect;
    }

    @PostMapping(value = "/addshopitem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addShopItem(
            @RequestParam(name = "shopitem_name") String name,
            @RequestParam(name = "shopitem_description", defaultValue = "No Description") String description,
            @RequestParam(name = "shopitem_price") int price,
            @RequestParam(name = "shopitem_amount") int amount,
            @RequestParam(name = "shopitem_stars") int stars,
            @RequestParam(name = "shopitem_pictureurl", defaultValue = "https://www.generationsforpeace.org/wp-content/uploads/2018/03/empty.jpg") String pictureURL,
            @RequestParam(name = "shopitem_pictureurllrg", defaultValue = "https://www.generationsforpeace.org/wp-content/uploads/2018/03/empty.jpg") String pictureURLlrg,
            @RequestParam(name = "top", defaultValue = "false") boolean top,
            @RequestParam(name = "brand_id", defaultValue = "0") int brand_id
            ) {

        Brand brand = shopItemService.getBrand(brand_id);
        if (brand != null) {
            ShopItem shopItem = new ShopItem();
            shopItem.setName(name);
            shopItem.setDescription(description);
            shopItem.setPrice(price);
            shopItem.setAmount(amount);
            shopItem.setStars(stars);
            shopItem.setPictureURL(pictureURL);
            shopItem.setPictureURLlrg(pictureURLlrg);
            shopItem.setTop(top);
            shopItem.setBrand(brand);
            shopItemService.addShopItem(shopItem);
        }

        return "redirect:/admin";
    }

    @PostMapping(value = "/addbrand")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addBrand(
            @RequestParam(name = "brand_name") String name,
            @RequestParam(name = "country_id", defaultValue = "0") int country_id
            ) {

        Country country = brandService.getCountry(country_id);
        if (country != null)
            brandService.addBrand(new Brand(0, name, country));

        return "redirect:/admin";
    }

    @PostMapping(value = "/addcountry")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addCountry(
            @RequestParam(name = "country_name") String name,
            @RequestParam(name = "country_code", defaultValue = "") String code
            ) {

        brandService.addCountry(new Country(0, name, code));

        return "redirect:/admin";
    }

    @PostMapping(value = "/addcategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addCategory(@RequestParam(name = "category_name") String name) {

        categoryService.addCategory(new Category(0, name));

        return "redirect:/admin";
    }

    @PostMapping(value = "/saveshopitem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String saveShopItem(
            @RequestParam(name = "shopitem_id", defaultValue = "0") int id,
            @RequestParam(name = "shopitem_name") String name,
            @RequestParam(name = "shopitem_description", defaultValue = "No Description") String description,
            @RequestParam(name = "shopitem_price") int price,
            @RequestParam(name = "shopitem_amount") int amount,
            @RequestParam(name = "shopitem_stars") int stars,
            @RequestParam(name = "shopitem_pictureurl", defaultValue = "https://www.generationsforpeace.org/wp-content/uploads/2018/03/empty.jpg") String pictureURL,
            @RequestParam(name = "shopitem_pictureurllrg", defaultValue = "https://www.generationsforpeace.org/wp-content/uploads/2018/03/empty.jpg") String pictureURLlrg,
            @RequestParam(name = "top", defaultValue = "false") boolean top,
            @RequestParam(name = "brand_id", defaultValue = "0") int brand_id
            ) {
        ShopItem shopItem = shopItemService.getShopItem(id);
        Brand brand = shopItemService.getBrand(brand_id);
        if (shopItem != null && brand != null) {
            shopItem.setName(name);
            shopItem.setDescription(description);
            shopItem.setPrice(price);
            shopItem.setAmount(amount);
            shopItem.setStars(stars);
            shopItem.setPictureURL(pictureURL);
            shopItem.setPictureURLlrg(pictureURLlrg);
            shopItem.setTop(top);
            shopItem.setBrand(brand);
            shopItemService.saveShopItem(shopItem);
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "/deleteshopitem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deleteShopItem(@RequestParam(name = "shopitem_id", defaultValue = "0") int id) {
        ShopItem shopItem = shopItemService.getShopItem(id);
        if (shopItem != null) {
            shopItemService.deleteShopItem(shopItem);
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "/deletepicture")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deletePicture(@RequestParam(name = "picture_id", defaultValue = "0") int id,
                                @RequestParam(name = "shopitem_id", defaultValue = "0") int shopitem_id) {
        String redirect = "error";
        Picture picture = pictureService.getPicture(id);
        if (picture != null) {
            pictureService.deletePicture(picture);
            redirect = "success";
        }
        return "redirect:/details/" + shopitem_id + "?" + redirect;
    }

    @PostMapping(value = "/assigncategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String assignCategory(
            @RequestParam(name = "shopitem_id", defaultValue = "0") int shopitem_id,
            @RequestParam(name = "category_id", defaultValue = "0") int category_id
            ) {
        ShopItem shopItem = shopItemService.getShopItem(shopitem_id);
        Category category = categoryService.getCategory(category_id);
        if (shopItem != null && category != null) {
            List<Category> categories = shopItem.getCategories();
            if (categories == null) {
                categories = new ArrayList<>();
            }
            categories.add(category);
            shopItemService.saveShopItem(shopItem);

            return "redirect:/details/" + shopitem_id;
        }

        return "redirect:/admin";
    }

    @PostMapping(value = "/removecategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String removeCategory(
            @RequestParam(name = "shopitem_id", defaultValue = "0") int shopitem_id,
            @RequestParam(name = "category_id", defaultValue = "0") int category_id
            ) {
        ShopItem shopItem = shopItemService.getShopItem(shopitem_id);
        Category category = categoryService.getCategory(category_id);
        if (shopItem != null && category != null) {
            List<Category> categories = shopItem.getCategories();
            if (categories != null) {
                categories.remove(category);
                shopItem.setCategories(categories);
                shopItemService.saveShopItem(shopItem);

                return "redirect:/details/" + shopitem_id;
            }
        }

        return "redirect:/admin";
    }

}
