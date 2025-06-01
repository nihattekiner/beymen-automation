# KAZIM NIHAT TEKINER
# beymen-automation and Trello Board
## **UI TESTS**

### **Senaryo: BeymenUITestCase Adımlarının Açıklanması**
1. URL'e `https://www.beymen.com/` gidilir.
2. Çerez (cookie) onaylama popup'ı reddedilir.
3. Cinsiyet seçimi kutucuğu kapatılır.
4. Title kontrol edilerek anasayfada olunduğu doğrulanır.
5. Arama bölümüne `şort` yazılır ve silinir.
6. Arama bölümüne `gömlek` yazılır ve klavyeden enter tuşuna basılır.
7. Listelenen ürünlerden rastgele bir ürün seçilir.
8. Seçilen ürünün adı ve fiyat bilgisi `product_info.txt` dosyasına kaydedilir.
9. Seçilen ürün sepete eklenir.
10. Ürün sayfasınadaki fiyat ile sepetteki fiyatın aynı olduğu doğrulanır.
11. Ürün adedi arttırılarak 2 yapılır ve doğrulanır.
12. Ürün sepetten silinir ve boş olduğu doğrulanır.

## **Çalıştırma Talimatları**

### **Ön Gereksinimler**
- Aşağıdaki yazılımların kurulu olduğundan emin olun:
    - Java (sürüm 8 veya üstü)
    - Maven
    - Uyumluluk için tarayıcı sürücüsü (örneğin, Chrome için ChromeDriver)

# PTrello API Test Otomasyonu

## **Genel Bakış**
Bu proje, Swagger Petstore API'si için CRUD (Oluşturma, Okuma, Güncelleme ve Silme) işlemlerini test etmek amacıyla oluşturulmuş bir otomasyon projesidir. Testler **Rest Assured** kullanılarak yazılmıştır ve doğrulama için **JUnit** kütüphanesi kullanılmıştır.

---

## **Kodun İşlevleri**
Bu sınıf, aşağıdaki test senaryolarını içerir:

### **1. Pet Oluşturma (Create)**
- **Test Adı:** `createPetPositive`
- **Açıklama:** API kullanılarak bir evcil hayvan (pet) oluşturulur. Yanıt gövdesi ve durum kodu doğrulanır.
- **Beklenen Durum Kodu:** `200`

- **Test Adı:** `createPetNegativeInvalidBody`
- **Açıklama:** Geçersiz bir JSON gövdesi gönderildiğinde, API'nin bir hata döndürmesi beklenir.
- **Beklenen Durum Kodu:** `400`

---

### **2. Pet Okuma (Read)**
- **Test Adı:** `readPetPositive`
- **Açıklama:** Var olan bir evcil hayvanın (pet) detayları alınır ve doğrulanır.
- **Beklenen Durum Kodu:** `200`

- **Test Adı:** `readPetNegativeNotFound`
- **Açıklama:** Var olmayan bir evcil hayvan sorgulandığında, API'nin `404 Not Found` döndürmesi beklenir.
- **Beklenen Durum Kodu:** `404`

---

### **3. Pet Güncelleme (Update)**
- **Test Adı:** `updatePetPositive`
- **Açıklama:** Mevcut bir evcil hayvanın detayları güncellenir ve değişikliklerin doğru olduğu doğrulanır.
- **Beklenen Durum Kodu:** `200`

---

### **4. Pet Silme (Delete)**
- **Test Adı:** `deletePetPositive`
- **Açıklama:** Mevcut bir evcil hayvan API kullanılarak silinir.
- **Beklenen Durum Kodu:** `200`

- **Test Adı:** `deletePetNegative_NotFound`
- **Açıklama:** Var olmayan bir evcil hayvan silinmeye çalışıldığında, API'nin `404 Not Found` döndürmesi beklenir.
- **Beklenen Durum Kodu:** `404`

---

## **Test Çalıştırma Talimatları**

### **Ön Gereksinimler**
- Java 8 veya üstü
- Maven
- IDE (IntelliJ IDEA, Eclipse vb.)
- Swagger Petstore API'nin erişilebilir olduğundan emin olun: `https://petstore.swagger.io/v2`