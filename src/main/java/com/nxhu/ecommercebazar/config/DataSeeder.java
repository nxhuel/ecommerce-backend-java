package com.nxhu.ecommercebazar.config;

import com.nxhu.ecommercebazar.modules.audit.persistence.entity.AuditLog;
import com.nxhu.ecommercebazar.modules.audit.persistence.repository.AuditLogRepository;
import com.nxhu.ecommercebazar.modules.category.persistence.entity.Category;
import com.nxhu.ecommercebazar.modules.category.persistence.repository.CategoryRepository;
import com.nxhu.ecommercebazar.modules.discount.persistence.entity.Discount;
import com.nxhu.ecommercebazar.modules.discount.persistence.repository.DiscountRepository;
import com.nxhu.ecommercebazar.modules.favorite.persistence.entity.Favorite;
import com.nxhu.ecommercebazar.modules.favorite.persistence.repository.FavoriteRepository;
import com.nxhu.ecommercebazar.modules.notification.persistence.entity.Notification;
import com.nxhu.ecommercebazar.modules.notification.persistence.repository.NotificationRepository;
import com.nxhu.ecommercebazar.modules.order.persistence.entity.Order;
import com.nxhu.ecommercebazar.modules.order.persistence.entity.OrderItem;
import com.nxhu.ecommercebazar.modules.order.persistence.repository.OrderItemRepository;
import com.nxhu.ecommercebazar.modules.order.persistence.repository.OrderRepository;
import com.nxhu.ecommercebazar.modules.permission.persistence.entity.Permission;
import com.nxhu.ecommercebazar.modules.permission.persistence.repository.PermissionRepository;
import com.nxhu.ecommercebazar.modules.product.persistence.entity.Product;
import com.nxhu.ecommercebazar.modules.product.persistence.entity.ProductImage;
import com.nxhu.ecommercebazar.modules.product.persistence.entity.ProductTag;
import com.nxhu.ecommercebazar.modules.product.persistence.repository.ProductImageRepository;
import com.nxhu.ecommercebazar.modules.product.persistence.repository.ProductRepository;
import com.nxhu.ecommercebazar.modules.product.persistence.repository.ProductTagRepository;
import com.nxhu.ecommercebazar.modules.review.persistence.entity.Review;
import com.nxhu.ecommercebazar.modules.review.persistence.repository.ReviewRepository;
import com.nxhu.ecommercebazar.modules.role.persistence.entity.Role;
import com.nxhu.ecommercebazar.modules.role.persistence.entity.RolePermission;
import com.nxhu.ecommercebazar.modules.role.persistence.repository.RolePermissionRepository;
import com.nxhu.ecommercebazar.modules.role.persistence.repository.RoleRepository;
import com.nxhu.ecommercebazar.modules.setting.persistence.entity.Setting;
import com.nxhu.ecommercebazar.modules.setting.persistence.repository.SettingRepository;
import com.nxhu.ecommercebazar.modules.user.persistence.entity.User;
import com.nxhu.ecommercebazar.modules.user.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductTagRepository productTagRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;
    private final NotificationRepository notificationRepository;
    private final AuditLogRepository auditLogRepository;
    private final SettingRepository settingRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Database already seeded - skipping");
            return;
        }
        log.info("Seeding database...");
        seedRoles();
        seedPermissions();
        seedUsers();
        seedCategories();
        seedDiscounts();
        seedProducts();
        seedOrders();
        seedReviews();
        seedFavorites();
        seedNotifications();
        seedAuditLogs();
        seedSettings();
        log.info("Database seeding complete!");
    }

    private Role OWNER, STAFF, CUSTOMER;

    private void seedRoles() {
        OWNER = roleRepository.save(Role.builder().name(Role.RoleName.OWNER).label("Dueño").build());
        STAFF = roleRepository.save(Role.builder().name(Role.RoleName.STAFF).label("Staff").build());
        CUSTOMER = roleRepository.save(Role.builder().name(Role.RoleName.CUSTOMER).label("Cliente").build());
    }

    private void seedPermissions() {
        List.of(
                "users:read", "users:write", "users:delete",
                "products:read", "products:write", "products:delete",
                "categories:read", "categories:write", "categories:delete",
                "orders:read", "orders:write", "orders:delete",
                "discounts:read", "discounts:write", "discounts:delete",
                "reviews:read", "reviews:delete",
                "settings:read", "settings:write"
        ).forEach(key -> {
            Permission p = permissionRepository.save(Permission.builder().key(key).build());
            rolePermissionRepository.save(RolePermission.builder().role(OWNER).permission(p).build());
            if (!key.contains("delete") && !key.equals("settings:write")) {
                rolePermissionRepository.save(RolePermission.builder().role(STAFF).permission(p).build());
            }
        });
    }

    private User OWNER_USER, STAFF_USER, CLIENTE_USER;

    private void seedUsers() {
        OWNER_USER = userRepository.save(User.builder()
                .name("Admin Bazar")
                .email("owner@bazar.com.ar")
                .password("owner123")
                .role(OWNER)
                .avatar("https://api.dicebear.com/7.x/avataaars/svg?seed=owner")
                .phone("11-5555-0001")
                .address("Av. Corrientes 1200, CABA")
                .build());
        STAFF_USER = userRepository.save(User.builder()
                .name("Maria Lopez")
                .email("staff@bazar.com.ar")
                .password("staff123")
                .role(STAFF)
                .avatar("https://api.dicebear.com/7.x/avataaars/svg?seed=staff")
                .phone("11-5555-0002")
                .address("Calle Florida 800, CABA")
                .build());
        CLIENTE_USER = userRepository.save(User.builder()
                .name("Carlos Garcia")
                .email("cliente@bazar.com.ar")
                .password("cliente123")
                .role(CUSTOMER)
                .avatar("https://api.dicebear.com/7.x/avataaars/svg?seed=cliente")
                .phone("11-5555-0003")
                .address("Av. Rivadavia 3400, CABA")
                .build());
    }

    private Category ELECTRONICA, HOGAR, MODA, DEPORTES, LIBROS, JUGUETES;

    private void seedCategories() {
        ELECTRONICA = categoryRepository.save(Category.builder().name("Electrónica").slug("electronica").icon("laptop").description("Productos electrónicos y tecnología").build());
        HOGAR = categoryRepository.save(Category.builder().name("Hogar").slug("hogar").icon("home").description("Artículos para el hogar").build());
        MODA = categoryRepository.save(Category.builder().name("Moda").slug("moda").icon("shirt").description("Ropa y accesorios").build());
        DEPORTES = categoryRepository.save(Category.builder().name("Deportes").slug("deportes").icon("sports_soccer").description("Equipamiento deportivo").build());
        LIBROS = categoryRepository.save(Category.builder().name("Libros").slug("libros").icon("menu_book").description("Libros y lectura").build());
        JUGUETES = categoryRepository.save(Category.builder().name("Juguetes").slug("juguetes").icon("toys").description("Juguetes y juegos").build());
    }

    private Discount DTO_VERANO, DTO_BIENVENIDA, DTO_FLASH;

    private void seedDiscounts() {
        DTO_VERANO = discountRepository.save(Discount.builder().code("VERANO25").percentage(25).active(true).validUntil(LocalDateTime.of(2026, 3, 31, 23, 59)).description("Descuento de verano").build());
        DTO_BIENVENIDA = discountRepository.save(Discount.builder().code("BIENVENIDA10").percentage(10).active(true).validUntil(LocalDateTime.of(2026, 12, 31, 23, 59)).description("Descuento bienvenida nuevos clientes").build());
        DTO_FLASH = discountRepository.save(Discount.builder().code("FLASH50").percentage(50).active(true).validUntil(LocalDateTime.of(2026, 2, 15, 23, 59)).description("Oferta flash 50%").build());
    }

    private Product P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15;

    private void seedProducts() {
        P1 = saveProduct("Smartwatch X200", "smartwatch-x200", ELECTRONICA, 15999.99, DTO_BIENVENIDA, 50, 4.5, 24, "TechBrand", "TCH-SW-001", true,
                List.of("https://picsum.photos/seed/smartwatch1/400/400", "https://picsum.photos/seed/smartwatch2/400/400"),
                List.of("smartwatch", "reloj", "tecnologia", "fitness"));
        P2 = saveProduct("Auriculares Bluetooth Pro", "auriculares-bluetooth-pro", ELECTRONICA, 8999.99, DTO_VERANO, 120, 4.3, 18, "SoundMax", "SDM-BT-002", true,
                List.of("https://picsum.photos/seed/auriculares1/400/400", "https://picsum.photos/seed/auriculares2/400/400"),
                List.of("audio", "bluetooth", "auriculares", "inalambrico"));
        P3 = saveProduct("Parlante Portátil Resistente", "parlante-portatil", ELECTRONICA, 12499.99, null, 30, 4.7, 42, "BoomBox", "BBX-SPK-003", true,
                List.of("https://picsum.photos/seed/parlante1/400/400", "https://picsum.photos/seed/parlante2/400/400"),
                List.of("audio", "parlante", "portatil", "bluetooth"));
        P4 = saveProduct("Set Sartenes Antiadherentes 5pz", "set-sartenes", HOGAR, 21999.99, DTO_FLASH, 25, 4.6, 31, "HogarChef", "HCH-SAR-004", false,
                List.of("https://picsum.photos/seed/sarten1/400/400", "https://picsum.photos/seed/sarten2/400/400"),
                List.of("cocina", "sartenes", "antiadherente", "hogar"));
        P5 = saveProduct("Lámpara LED Escritorio", "lampara-led-escritorio", HOGAR, 6999.99, null, 80, 4.2, 15, "LightHome", "LHT-LED-005", false,
                List.of("https://picsum.photos/seed/lampara1/400/400"),
                List.of("lampara", "led", "escritorio", "iluminacion"));
        P6 = saveProduct("Organizador Cocina Multiuso", "organizador-cocina", HOGAR, 3499.99, DTO_BIENVENIDA, 200, 4.0, 9, "HomePlus", "HPL-ORG-006", false,
                List.of("https://picsum.photos/seed/organizador1/400/400"),
                List.of("cocina", "organizador", "hogar", "almacenamiento"));
        P7 = saveProduct("Campera Impermeable Trekking", "campera-impermeable", MODA, 28999.99, null, 35, 4.8, 56, "OutdoorFit", "ODF-CMP-007", true,
                List.of("https://picsum.photos/seed/campera1/400/400", "https://picsum.photos/seed/campera2/400/400"),
                List.of("campera", "impermeable", "trekking", "moda"));
        P8 = saveProduct("Zapatillas Running Aero", "zapatillas-running-aero", MODA, 45999.99, DTO_VERANO, 40, 4.6, 38, "SpeedRun", "SPR-ZAP-008", true,
                List.of("https://picsum.photos/seed/zapatillas1/400/400", "https://picsum.photos/seed/zapatillas2/400/400"),
                List.of("zapatillas", "running", "deporte", "moda"));
        P9 = saveProduct("Mochila Urbana 30L", "mochila-urbana", MODA, 12999.99, null, 65, 4.4, 22, "CityPack", "CTP-MCH-009", false,
                List.of("https://picsum.photos/seed/mochila1/400/400"),
                List.of("mochila", "urbana", "moda", "accesorio"));
        P10 = saveProduct("Pelota Fútbol Profesional", "pelota-futbol", DEPORTES, 8499.99, null, 90, 4.1, 12, "GoalMax", "GLM-PEL-010", false,
                List.of("https://picsum.photos/seed/pelota1/400/400"),
                List.of("futbol", "pelota", "deporte", "profesional"));
        P11 = saveProduct("Pesas Ajustables 20kg", "pesas-ajustables", DEPORTES, 32999.99, DTO_FLASH, 15, 4.9, 47, "IronFit", "IRN-PES-011", true,
                List.of("https://picsum.photos/seed/pesas1/400/400", "https://picsum.photos/seed/pesas2/400/400"),
                List.of("pesas", "gimnasio", "fitness", "deporte"));
        P12 = saveProduct("Yoga Mat Premium 6mm", "yoga-mat-premium", DEPORTES, 5499.99, DTO_BIENVENIDA, 110, 4.3, 19, "ZenFit", "ZFT-YOG-012", false,
                List.of("https://picsum.photos/seed/yoga1/400/400"),
                List.of("yoga", "mat", "fitness", "ejercicio"));
        P13 = saveProduct("El Principito - Edición Especial", "el-principito", LIBROS, 4999.99, null, 60, 4.9, 88, "PenguinLibros", "PGL-LIB-013", false,
                List.of("https://picsum.photos/seed/libro1/400/400"),
                List.of("libro", "clasico", "infantil", "coleccion"));
        P14 = saveProduct("Juego Construcción 500 piezas", "juego-construccion-500", JUGUETES, 11999.99, null, 45, 4.5, 27, "BuildFun", "BDF-JUE-014", true,
                List.of("https://picsum.photos/seed/juguete1/400/400", "https://picsum.photos/seed/juguete2/400/400"),
                List.of("construccion", "juguete", "educativo", "creativo"));
        P15 = saveProduct("Robot Educativo Programable", "robot-educativo", JUGUETES, 24999.99, DTO_VERANO, 20, 4.7, 33, "RoboKids", "RBK-RBT-015", true,
                List.of("https://picsum.photos/seed/robot1/400/400", "https://picsum.photos/seed/robot2/400/400"),
                List.of("robot", "educativo", "programable", "stem"));
    }

    private Product saveProduct(String name, String slug, Category category, double price, Discount discount,
                                 int stock, double rating, int reviewCount, String brand, String sku, boolean featured,
                                 List<String> images, List<String> tags) {
        Product product = productRepository.save(Product.builder()
                .name(name).slug(slug).category(category)
                .price(BigDecimal.valueOf(price)).discount(discount)
                .stock(stock).rating(rating).reviewCount(reviewCount)
                .brand(brand).sku(sku)
                .description("Descripción detallada de " + name + ". Producto de alta calidad ideal para tu día a día. " +
                        "Fabricado con materiales premium. Garantía oficial de 12 meses.")
                .featured(featured).build());
        for (int i = 0; i < images.size(); i++) {
            productImageRepository.save(ProductImage.builder()
                    .product(product).imageUrl(images.get(i)).sortOrder(i).build());
        }
        for (String tag : tags) {
            productTagRepository.save(ProductTag.builder().product(product).tag(tag).build());
        }
        return product;
    }

    private void seedOrders() {
        Order o1 = orderRepository.save(Order.builder()
                .user(CLIENTE_USER).status(Order.OrderStatus.DELIVERED)
                .total(BigDecimal.valueOf(45999.99)).paymentMethod("credit_card")
                .shippingAddress("Av. Rivadavia 3400, CABA").build());
        orderItemRepository.save(OrderItem.builder().order(o1).product(P7).quantity(1).price(BigDecimal.valueOf(28999.99)).build());
        orderItemRepository.save(OrderItem.builder().order(o1).product(P12).quantity(1).price(BigDecimal.valueOf(5499.99)).build());
        orderItemRepository.save(OrderItem.builder().order(o1).product(P5).quantity(2).price(BigDecimal.valueOf(6999.99)).build());

        Order o2 = orderRepository.save(Order.builder()
                .user(CLIENTE_USER).status(Order.OrderStatus.PROCESSING)
                .total(BigDecimal.valueOf(23999.98)).paymentMethod("debit_card")
                .shippingAddress("Av. Rivadavia 3400, CABA").build());
        orderItemRepository.save(OrderItem.builder().order(o2).product(P2).quantity(1).price(BigDecimal.valueOf(8999.99)).build());
        orderItemRepository.save(OrderItem.builder().order(o2).product(P14).quantity(1).price(BigDecimal.valueOf(11999.99)).build());

        Order o3 = orderRepository.save(Order.builder()
                .user(STAFF_USER).status(Order.OrderStatus.PENDING)
                .total(BigDecimal.valueOf(15999.99)).paymentMethod("mercado_pago")
                .shippingAddress("Calle Florida 800, CABA").build());
        orderItemRepository.save(OrderItem.builder().order(o3).product(P1).quantity(1).price(BigDecimal.valueOf(15999.99)).build());

        Order o4 = orderRepository.save(Order.builder()
                .user(CLIENTE_USER).status(Order.OrderStatus.SHIPPED)
                .total(BigDecimal.valueOf(5499.99)).paymentMethod("cash")
                .shippingAddress("Av. Rivadavia 3400, CABA").build());
        orderItemRepository.save(OrderItem.builder().order(o4).product(P15).quantity(1).price(BigDecimal.valueOf(24999.99)).build());

        Order o5 = orderRepository.save(Order.builder()
                .user(CLIENTE_USER).status(Order.OrderStatus.CANCELLED)
                .total(BigDecimal.valueOf(0)).paymentMethod("credit_card")
                .shippingAddress("Av. Rivadavia 3400, CABA").build());

        List.of(o1, o2, o3, o4, o5).forEach(o -> {
            auditLogRepository.save(AuditLog.builder()
                    .user(o.getUser()).action("CREATE_ORDER").entity("Order:" + o.getId()).build());
        });
    }

    private void seedReviews() {
        reviewRepository.save(Review.builder().product(P1).user(CLIENTE_USER).rating(5).comment("Excelente reloj, muy completo y preciso. La batería dura varios días.").build());
        reviewRepository.save(Review.builder().product(P7).user(CLIENTE_USER).rating(4).comment("Muy buena campera, abriga mucho y es realmente impermeable.").build());
        reviewRepository.save(Review.builder().product(P11).user(STAFF_USER).rating(5).comment("Las mejores pesas que compré. Ocupan poco espacio y son muy versátiles.").build());
        reviewRepository.save(Review.builder().product(P13).user(CLIENTE_USER).rating(5).comment("Edición hermosa, llegó en perfecto estado. Muy recomendable.").build());
        reviewRepository.save(Review.builder().product(P8).user(CLIENTE_USER).rating(4).comment("Muy cómodas para correr. Buena amortiguación.").build());
        reviewRepository.save(Review.builder().product(P3).user(STAFF_USER).rating(5).comment("Potente sonido, la batería es increíble. Ideal para exteriores.").build());
    }

    private void seedFavorites() {
        favoriteRepository.save(Favorite.builder().user(CLIENTE_USER).product(P8).build());
        favoriteRepository.save(Favorite.builder().user(CLIENTE_USER).product(P15).build());
        favoriteRepository.save(Favorite.builder().user(CLIENTE_USER).product(P3).build());
        favoriteRepository.save(Favorite.builder().user(STAFF_USER).product(P1).build());
        favoriteRepository.save(Favorite.builder().user(STAFF_USER).product(P11).build());
    }

    private void seedNotifications() {
        notificationRepository.save(Notification.builder().user(CLIENTE_USER).title("Pedido enviado 🚚")
                .message("Tu pedido #4 ha sido enviado y está en camino.").read(false).build());
        notificationRepository.save(Notification.builder().user(CLIENTE_USER).title("Bienvenido 🎉")
                .message("Gracias por registrarte en Bazar. Disfrutá de tus compras!").read(true).build());
        notificationRepository.save(Notification.builder().user(CLIENTE_USER).title("Descuento disponible ⚡")
                .message("Usá el código FLASH50 para obtener 50% OFF en productos seleccionados.").read(false).build());
        notificationRepository.save(Notification.builder().user(STAFF_USER).title("Nuevo pedido recibido")
                .message("El cliente Carlos Garcia realizó un nuevo pedido.").read(false).build());
        notificationRepository.save(Notification.builder().user(OWNER_USER).title("Stock bajo ⚠️")
                .message("El producto 'Robot Educativo Programable' tiene solo 20 unidades en stock.").read(false).build());
    }

    private void seedAuditLogs() {
        auditLogRepository.save(AuditLog.builder().user(OWNER_USER).action("LOGIN").entity("User:" + OWNER_USER.getId()).build());
        auditLogRepository.save(AuditLog.builder().user(STAFF_USER).action("LOGIN").entity("User:" + STAFF_USER.getId()).build());
        auditLogRepository.save(AuditLog.builder().user(CLIENTE_USER).action("REGISTER").entity("User:" + CLIENTE_USER.getId()).build());
        auditLogRepository.save(AuditLog.builder().user(STAFF_USER).action("UPDATE_PRODUCT").entity("Product:" + P1.getId()).build());
        auditLogRepository.save(AuditLog.builder().user(OWNER_USER).action("CREATE_DISCOUNT").entity("Discount:FLASH50").build());
        auditLogRepository.save(AuditLog.builder().user(CLIENTE_USER).action("ADD_REVIEW").entity("Product:" + P1.getId()).build());
        auditLogRepository.save(AuditLog.builder().user(CLIENTE_USER).action("LOGIN").entity("User:" + CLIENTE_USER.getId()).build());
    }

    private void seedSettings() {
        settingRepository.save(Setting.builder()
                .storeName("Bazar Online")
                .currency("ARS")
                .city("Buenos Aires")
                .country("Argentina")
                .email("contacto@bazar.com.ar")
                .phone("11-5555-0000")
                .address("Av. Corrientes 1200, CABA")
                .build());
    }
}
