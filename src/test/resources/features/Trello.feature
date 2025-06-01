Feature: Trello API Testleri

  Scenario: Trello board oluştur
    Given Trello API için board oluşturma isteği hazırlanır
    When Board oluşturma isteği gönderilir
    Then Board başarıyla oluşturulur ve board ID kaydedilir

  Scenario: Board'a iki kart ekle
    Given Trello board ID ile kart oluşturma isteği hazırlanır
    When Birinci kart için POST isteği gönderilir
    Then Birinci kart başarıyla oluşturulur ve kart ID kaydedilir
    When İkinci kart için POST isteği gönderilir
    Then İkinci kart başarıyla oluşturulur ve kart ID kaydedilir

  Scenario: Rastgele seçilen bir kart güncelle
    Given Rastgele seçilen bir kart için güncelleme isteği hazırlanır
    When Kart güncelleme isteği gönderilir
    Then Kart başarıyla güncellenir

  Scenario: Oluşturulan kartları sil
    Given Silinecek kartlar için DELETE isteği hazırlanır
    When Kartlar için silme isteği gönderilir
    Then Kartlar başarıyla silinir

  Scenario: Oluşturulan board'u sil
    Given Silinecek board için DELETE isteği hazırlanır
    When Board silme isteği gönderilir
    Then Board başarıyla silinir
