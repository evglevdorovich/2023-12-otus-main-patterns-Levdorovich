### Homework 8

Описание задание:
Задача Endpoint принять входящее сообщение от Агента, десериализовать его и передать его на обработку внутрь игрового сервера.

Шаги выполнения ДЗ:

1. Определить формат сообщений, которые отправляет Агент игровому серверу.
   Указание: правила во всех играх могут сильно отличаться друг от друга, поэтому нужно подумать о таком формате,
   который бы не зависел от текущей реализации конкретных команд,
   то есть чтобы endpoint не приходилось модифицировать каждый раз, когда мы разработаем новое правило игры.
   Как идею для решения поставленной задачи можно рассмотреть следующий набор данных, который стоит передавать в сообщении:
   I) id игры - для идентификации игры, в рамках которой это сообщение обработано. С помощью этого id можно будет определить получателя сообщения при
   маршрутизации сообщения внутри игрового сервера.
   II) id игрового объекта, которому адресовано сообщение. С помощью этого id можно будет определить игровой объект внутри игры, для которого
   адресовано
   это сообщение.
   III) id операции - по этому id в IoC можно будет определить команду, которая будет обрабатывать данное сообщение.
   IV) args - вложенный json объект с параметрами операции. Содержимое этого объекта полностью зависит от команды, которая будет применяться к
   игровому
   объекту.
2. Определить endpoint, который принимает входящее сообщение и конвертирует в команду InterpretCommand.
   enpoint должен определить игру, которой адресовано сообщение, создать команду InterpretCommand и поместить эту команду в очередь команд этой игры.
   Команда InterpretCommand получает всю информацию об операции, которую необходимо выполнить, параметрах и объекте, над которым эта операция будет
   выполняться.
3. Задача команды InetrpretCommand на основе IoC контейнера создать команду для нужного объекта, которая которая соответствует приказу,
   содержащемуся в сообщении и постановки этой команды в очереsдь команд игры.
   Например, если сообщение указано, что объект с id 548 должен начать двигаться, то результат InterpretCommand заключается можно описать следующим
   псевдокодом
   var obj = IoC.Resolve("Игровые объекты", "548"); // "548" получено из входящего сообщения
   IoC.Resolve("Установить начальное значение скорости", obj, 2); // значение 2 получено из args переданного в сообщении
   var cmd = IoC.Resolve("Движение по прямой", obj); // Решение, что нужно выполнить движение по прямой получено из сообщения
   // обратите внимание само значение "Движение по прямой" нельзя читать на прямую из сообщения,
   // чтобы избежать инъекции, когда пользователь попытается выполнить действие, которое ему выполнять не позволено
   IoC.Resolve("Очередь команд", cmd).Execute(); // Выполняем команду, которая поместит команду cmd в очередь команд игры.


