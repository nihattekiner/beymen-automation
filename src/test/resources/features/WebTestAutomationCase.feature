Feature:Insider UI Test Case

  Background:

    Given Setup Driver "chrome"
    And   Go to "https://www.beymen.com/" address

  @Deneme
  Scenario: Search and cart operations on Beymen

    Given the homepage should be displayed
    When the user searchs "şort" into the search box and clears the search box
    And the user searchs "gömlek" into the search box and presses the Enter Key
    And a random product from the search results is selected
    And the selected product is added to the cart
    Then the product price on the product page should match the price in the cart
    When the product quantity is increased to 2
    Then the product quantity should be 2 in the cart
    When the product is removed from the cart
    Then the cart should be empty



  #www.beymen.com sitesi açılır.
  #Ana sayfanın açıldığı kontrol edilir.
  #Arama kutucuğuna “şort” kelimesi girilir.(Not = Şort kelimesi excel dosyası
  #üzerinden 1 sütun 1 satırdan alınarak yazılmalıdır. )
  #Arama kutucuğuna girilen “şort” kelimesi silinir.
  #Arama kutucuğuna “gömlek” kelimesi girilir.(Not = Gömlek kelimesi excel dosyası
  #üzerinden 2 sütun 1 satırdan alınarak yazılmalıdır. )
  #Klavye üzerinden “enter” tuşuna bastırılır
  #Sonuca göre sergilenen ürünlerden rastgele bir ürün seçilir.
  #Seçilen ürünün ürün bilgisi ve tutar bilgisi txt dosyasına yazılır.
  #Seçilen ürün sepete eklenir.
  #Ürün sayfasındaki fiyat ile sepette yer alan ürün fiyatının doğruluğu karşılaştırılır.
  #Adet arttırılarak ürün adedinin 2 olduğu doğrulanır.
  #Ürün sepetten silinerek sepetin boş olduğu kontrol edilir.






