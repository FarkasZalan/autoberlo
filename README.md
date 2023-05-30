# Az alkalmazásról

### Sima felhasználó
Az alkalmazásban meg van valósítva a főoldalon való szűrés időintervallum szerint a szabad autókra.
A szabad illetve elérhető autókat le lehet foglalni a kötelező adatok megadása után (ha nem szűrünk rá a szabad autókra akkor csak a nem innaktív autókat hozza fel de ha rámegyünk egy autóra és olyan intervallumot adunk meg amikor épp foglalt akkor sem engedi lefoglalni)

### Admin
Az admin felületet elérhetjüj a menüből is az Admin fülre kattintva vagy pedig a /admin elérési úton.
Az admin a menün át el lehet érni a leadott foglalásokat valamint az összes autót.
A foglalásokat nem lehet kézzel lemondani vagy módosítani de ha innaktiválunk egy autót, akkor az összes hozzá tartozó foglalás is lemondásra kerül de ha újra aktiváljuk akkor nem lesznek újra aktívak a foglalások, minden felhasználónak újra le kell foglalnia az autót amikorra szeretné.
Lehet az autók minden adatát módosítani illetve lehet új kocsit is létrehozni illetve ahogy fentebb is írtam innaktiválni is őket (ha innaktiválva van egy autó akkor a főoldalon lévő listában sem jelenik meg szűrés hatására sem csak az admin láthatja onnantól).

### Egyéb tudnivalók
Meg vannak valósítva JUnit tesztek is a kódban minden controllerhez, az admin funkciókhoz és a felhasználó funkciókhoz is egyaránt.
Az alkalmazás elkészítéséhez a következőket használtam: 
- Spring boot
- MySql
- Thymeleaf

Ezáltal jelezném, hogy elolvastam a leírás végét is és remélem így is érvényes az a plusz 10 pont ;)
