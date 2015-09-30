# YandexMoneyClient

# Скриншоты
<img src="https://raw.github.com/KirillTim/YandexMoneyClient/master/screenshots/2015-09-29 21.37.03.png" alt="screenshot1" width="200">
<img src="https://raw.github.com/KirillTim/YandexMoneyClient/master/screenshots/2015-09-29 21.39.23.png" alt="screenshot1" width="200">
<img src="https://raw.github.com/KirillTim/YandexMoneyClient/master/screenshots/2015-09-29 21.40.56.png" alt="screenshot1" width="200">
<img src="https://raw.github.com/KirillTim/YandexMoneyClient/master/screenshots/2015-09-29 21.41.39.png" alt="screenshot1" width="200">
<img src="https://raw.github.com/KirillTim/YandexMoneyClient/master/screenshots/2015-09-29 21.41.53.png" alt="screenshot1" width="200">
# Возможности
Приложение умеет переводить деньги на счёт пользователя яндекс денег, предупреждает его, если пользователя, которому хотят сделать перевод не существует. Приложение автоматически пересчитывает поля Total и To be paid в поле ввода и автоматически проверяет, что хватит денег на перевод(выводит сообщение, если не хватает и не даёт возможность совершить перевод)
Приложение умеет определять тип аккаунта пользователя и говорить ему, что он должен верефицировать себя, чтобы пользоваться приложением если он этого ещё не сделал.
Приложение умеет открывать прямые платёжные ссылки на сайт money.yandex.ru(первый скриншот)
Приложение показывает историю, расширенную информацию при тапе на элемент списка. Модные стрелочки разных цветов показывают направление перевода и его статус.
Токен хранится в зашифрованном виде. имеется возможность сбросить код блокировки.

# Детали реализации
Использованы библиотеки(не гугловские): EventBus и Yandex.Money Java SDK.
Зачем EventBus если можно было использовать IntentService и BroadCastReceiver, который шлёт результат? 
Потому что:
a)У EventBus значительно шире и понятнее API. всего 3 типа действий: зарегестрировать слушателя, поймать событие и обработать в определённом потоке, отправить событие)
b)Понятнее. Когда кто-то будет читать код, написанный с использованием собственных велосипедов(своя шина событий поверх BrodcastReceiver) ему будет трудно понять, как именно выполняется какой-либо код(в каком потоке, как оттуда послать результат определённому потоку, итд). 
с)Безопаснее. Код библиотеки хорошо протестирован и в нём исправлено множество багов. Писать самому - получать или сырой код с багами или тратить большое время на тестирование.
Зачем SDK, можно же использовать HTTPClient.
Зачем писать забагованные велосипеды если можно не писать? Нужно уметь правильно выбирать инструмены. Понятно, что если приложение МНОГО общается с сетью, то использовать не нативные библиотеки не всегда оправдано. Но, обычно, а Android приложении нет большого взаимодействия с сетью, а значит производительность не столь критична и тогда можно использовать асинхронные http библиотеки, которые сделают код чище и быстрее.SDK написан на основе OKHHTP и повышает уровень
абстракции.Повышение уровня абстракции - всегда хорошо. Кроме того SDK покрыт тестами, которые позволяют считать его надёжным.
# Как надо было делать(aka опыт -- сын ошибок трудных)
Сделать parcelable модель(используя библиотеку для сериализации) для каждого экрана и каждого фрагмента, использовать Android Binding(написать немного кода, чтобы сделать binding двухсторонним, как в PaymentActivity) для отрисовки. Сделать модель для записи истории и, опять же, использовать RecyclerView и биндинги для элемента. Перенести слушателей eventbus'a в base class.

# Известные проблемы
Иногда происходят сбои авторизации. И приложение показывает бесконечный процесс загрузки. Это можно починить удавлив данные приложения(в деспетчере приложений) и залогинившись опять.
Плохое тестовое покрытие(из-за нехватки времени :( )
