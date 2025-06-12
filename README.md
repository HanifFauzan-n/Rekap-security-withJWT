# Spring security + JWT

| Anotasi                    | Fungsi                                                                        |
| -------------------------- | ----------------------------------------------------------------------------- |
| `@EnableWebSecurity`       | Mengaktifkan fitur Spring Security untuk konfigurasi keamanan web             |
| `@EnableMethodSecurity`    | Mengaktifkan keamanan berbasis anotasi di level method (`@PreAuthorize`, dll) |
| `@Configuration`           | Menandai class sebagai konfigurasi (digunakan untuk SecurityConfig)           |
| `@Bean`                    | Menandai method yang menghasilkan bean yang dikelola oleh Spring              |
| `@Autowired`               | Menyuntikkan dependency secara otomatis                                       |
| `@Component`               | Menandai class sebagai bean biasa yang bisa di-scan Spring                    |
| `@Service`                 | Menandai class sebagai service layer                                          |
| `@Repository`              | Menandai class sebagai repository, sekaligus aktivasi exception handler JPA   |
| `@RestController`          | Menandai class sebagai controller REST (otomatis mengembalikan JSON)          |
| `@RequestMapping`          | Menentukan mapping URL root pada controller                                   |
| `@PostMapping`             | Mapping HTTP POST ke method tertentu                                          |
| `@GetMapping`              | Mapping HTTP GET ke method tertentu                                           |
| `@DeleteMapping`           | Mapping HTTP DELETE ke method tertentu
| `@RequestBody`             | Menyatakan bahwa parameter method berasal dari body permintaan (JSON)         |
| `@Valid`                   | Memicu validasi terhadap data yang masuk (digunakan bersama DTO)              |
| `@Slf4j`                   | Anotasi Lombok untuk menambahkan logger `log`                                 |
| `@RequiredArgsConstructor` | Anotasi Lombok untuk membuat constructor otomatis untuk field `final`         |
| `@CrossOrigin`             | Mengizinkan permintaan dari domain luar (CORS)                                |

# Email Service

| Anotasi / Komponen  | Fungsi                                                            |
| ------------------- | ----------------------------------------------------------------- |
| `JavaMailSender`    | Spring komponen untuk mengirim email.                             |
| `MimeMessage`       | Objek email lengkap (bisa HTML, lampiran, dll).                   |
| `MimeMessageHelper` | Membantu menyusun isi email (subjek, penerima, konten HTML, dll). |

# Jwt Utils

| Method              | Fungsi                                               |
| ------------------- | ---------------------------------------------------- |
| `generateToken()`   | Buat token berdasarkan username, dengan expired time |
| `extractUsername()` | Ambil username dari token JWT                        |
| `isTokenValid()`    | Validasi token (expired atau tidak)                  |


