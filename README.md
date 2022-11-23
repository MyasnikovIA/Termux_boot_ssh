# Termux_boot_ssh
Termux 0.118.0 Добавлен автозапуск, и выполнение скриптов при запуске

<h4>Собранный APK можно скачать https://disk.yandex.ru/d/7SP_x0Jhsb2FFA</h4>

<pre>
После утсановки программы, появляется возможность использования  андроид устройства в качести  Linux сервера.
Есть возможность написания и запуска программ на языках Python3.11, PHP, NodeJS (после установки пакетов "pkg install python -y")
Для автозапуска необходимо прописать однострочную команду в файл /data/data/com.termux/files/home/.termux/boot/autorun (пример создается при первом запуске) 

Ключь SSH необходимо поместить в каталог  /data/data/com.termux/files/home/.ssh/authorized_keys (cat termux.pub >> authorized_keys)
Для доступа SSH необходимо прописать пароль в системе  командой "passwd" в консоли

Сгененрировать ключ "termux.pub" можно через программу Bitvise SSH Client
<img src="https://github.com/MyasnikovIA/Termux_boot_ssh/blob/main/img.png?raw=true"/>


</pre>

Источник: https://github.com/termux/termux-app/releases
<pre>
Изменения от оригинала: 
1) Добавил автозапуск (Есть проблемы с Android v12).
2) Добавил выполнение скрипта в из каталога /data/data/com.termux/files/home/.termux/boot/<любой текстовой файл> 
    Файлы будут выбираться по очереди, и вычитывается построчно. Каждая строка запускается отдельно в новом потоке.
    "#" - однострочный комментарий .
3) После инсталляции добавлаются пакеты "MC" "OPENSSH".
</pre>
