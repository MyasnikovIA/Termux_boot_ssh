Установка пароля для для входа через SSH:
$passwd
Запуск SSH:
$sshd

-----
Подключение 
$termux-setup-storage

-----
Установка JAVA:
$ apt update
$ apt upgrade
$ apt install wget
$ wget https://archive.org/download/openjdk-9-jre-headless_9.2017.8.20-1_x86_64/openjdk-9-jdk-headless_9.2017.8.20-1_arm.deb
$ apt-get install -y ./openjdk-9-jdk-headless_9.2017.8.20-1_arm.deb
$ apt-get install -y ./openjdk-9-jre-headless_9.2017.8.20-1_arm.deb
$ apt install ecj
$ apt install dx


-----
Установка Java
pkg install openjdk-17 -y 


-----
Установка Pytnon

pip без SSH
python -m pip install -U --index-url=http://pypi.python.org/simple/ --trusted-host pypi.org --trusted-host pypi.python.org --trusted-host files.pythonhosted.org <packetName>

pip install pip-22.3.1-py3-none-any.whl
pip install pip-22.3.1-py3-none-any.whl  --no-index --find-links file:///data/data/com.termux/files/home/storage/py/Android_ptz/whl
pip install -r requirements.txt  --no-index --find-links file:///data/data/com.termux/files/home/storage/py/Android_ptz/whl

// выгрузить библиотеки PIP в whl файл
pip freeze > requirements.txt
pip download -r requirements.txt -d /data/data/com.termux/files/home/storage/whl


# выгрузить список всех доступных пакетов
# pip freeze > requirements.txt

# Скачивание  пакетов в локальный репазиторий
# pip download -r requirements.txt -d whl

# Установка паркетов из локального репазитория
# pip install -r requirements.txt  --no-index --find-links file:///data/data/com.termux/files/home/storage/py/Android_ptz/whl

pip download flask -d /data/data/com.termux/files/home/storage/whl
pip download psycopg2 -d /data/data/com.termux/files/home/storage/whl
pip download requests -d /data/data/com.termux/files/home/storage/whl
pip download six -d /data/data/com.termux/files/home/storage/whl
pip download urllib3 -d /data/data/com.termux/files/home/storage/whl
pip download  cx-Oracle  -d /data/data/com.termux/files/home/storage/whl
pip download  certifi  -d /data/data/com.termux/files/home/storage/whl
pip download  charset-normalizer -d /data/data/com.termux/files/home/storage/whl
pip download  click -d /data/data/com.termux/files/home/storage/whl



wget https://github.com/Termux-pod/termux-pod/raw/main/aarch64/python/python-3.9.7/python_3.9.7_aarch64.deb?raw=true  (MAX)
dpkg --install ./python_3.9.7_aarch64.deb?raw=true
wget https://github.com/Termux-pod/termux-pod/blob/main/arm/python/python-3.9.7/python_3.9.7_arm.deb?raw=true         (mini)
dpkg --install ./python_3.9.7_arm.deb?raw=true

=========================================================================
Последовательность установки Python3.9.7  X96Max_VPN
=========================================================================
termux-change-repo
pkg install android-tools -y
pkg install wget -y
wget https://github.com/Termux-pod/termux-pod/blob/main/arm/python/python-3.9.7/python_3.9.7_arm.deb?raw=true
dpkg --install ./python_3.9.7_arm.deb?raw=true
pkg install libcrypt
apt --fix-broken install
dpkg --install ./python_3.9.7_arm.deb?raw=true
pkg update
apt list --upgradable
termux-change-repo
apt list --upgradable
pip install --upgrade pip
pip install flask  --no-index --find-links file:///data/data/com.termux/files/home/storage/whl
pkg install cmake -y   
pkg install python-numpy -y
pkg install opencv-python -y
pip install psycopg2
pip install pgdb
pip install cx_Oracle
pip install requests

=========================================================================

=========================================================================
Последовательность установки Python3.9.7  X96Mini
=========================================================================
termux-change-repo
pkg update -y  && \
apt list --upgradable  && \
pkg install android-tools -y && \
pkg install wget -y  && \
pkg install python -y && \
pkg install cmake -y && \
pkg install python-numpy -y && \
pkg install opencv-python -y && \
pkg install postgresql make clang vim curl git tree -y && \
pkg install stunnel -y && \
pkg install root-repo -y && \
pkg install iptables -y && \
apt install gdbm -y && \
apt autoremove -y && \
apt --fix-broken install -y && \
pip install --upgrade pip  && \
pip install psycopg2   && \
pip install pgdb && \
pip install cx_Oracle && \
pip install requests && \
pip install flask

=========================================================================


=========================================================================
-- Работа с postgres
-- https://www.youtube.com/watch?v=SOa-pyUMZtc&ab_channel=OsChannel
=========================================================================
apt install postgresql
pkg install postgresql
mkdir postgres
initdb ./postgres
pg_ctl -D ./postgres -l logfile start       -- запустить БД
pg_ctl -D /data/data/com.termux/files/home/postgres -l logfile start       -- запустить БД
pg_ctl -D /data/data/com.termux/files/home/postgres -l logfile stop       -- запустить БД
pg_ctl -D ./postgres -l logfile start       -- запустить БД
pg_ctl -D ./postgres -l stop                -- остановить БД
ps aux                                      -- показать список процессов
createdb d3                           -- создать БД
# Создать нового пользователя с паролем
--- createuser --superuser --pwprompt d3user   -- установить пароль суперюзера
--- createuser -h 127.0.0.1 -p 5432 -S -D -R -e d3user
# Создать нового пользователя с паролем в консоли с диалогами
createuser d3user
createuser --interactive d3user
--------------------------------------------------------------

psql -d d3                            -- подключится к БД
hello_bd=# \с postgres                      -- переключить БД
row_to_json(row(1,'foo'))

Инструкция  редактирования файла pg_hba.conf
https://postgrespro.ru/docs/postgresql/9.6/auth-pg-hba-conf
# Подключиться из локальной сети к любой БД /data/data/com.termux/files/home/postgres/pg_hba.conf
host    all        all             192.168.0.0/24         md5
# Добавить в postgresql.conf
listen_addresses = '*'

=========================================================================

=========================================================================
-----------
php8
pkg update $$ upgrade && \
pkg install -y  php php-fpm php-apache && \
dpkg -l | grep php && \
dpkg -l | grep php | cut -b 4-35 && \
apt purge php php-apache php-fpm -y
-----------
PHP7
wget https://github.com/take-i/termux-php7/raw/master/php7.4.12-aarch64-deb.zip && \
unzip php7.4.12-aarch64-deb.zip && \
cd php7.4.12-aarch64-deb.deb/  && \
rm php-pgsql*  && \
dpkg -i ./php* ./libicu_67.1_aarch64.deb
-----------
dpkg -l | egrep "php|libicu"
-----------
apt-mark showhold && \
libicu && \
php && \
php-apache && \
php-fpm
-----------

pkg install apache
pkg install php
apt install php-apache
cd /data/data/com.termux/files/usr/etc/apache2/
nano httpd.conf

sh -c "$(curl -fsSL https://html.sqlsec.com/termux-install.sh)"

=========================================================================
описание кодов кнопок
https://blog.csdn.net/u010164190/article/details/50551105
-----
 -----------------------------------
 Узнать имя запускаемого класса
 J:\Android\SDK\build-tools\30.0.1\aapt dump badging  C:\Users\MyasnikovIA\Downloads\Wink.apk
 ишим слово "launchable"
 -----------------------------------
 am start -n com.hkw.simplelauncher/android.hardware.faketouch
 -----------------------------------
 онлайн разбор  APK файла
 http://www.javadecompilers.com/
 -----------------------------------
  Имя пользователя
 ~$ whoami
 ssh -p 8022 u0_a207@192.168.1.128
 -----------------------------------

-----------------------------------
Список пакетов:
pm list packages
-----------------------------------
Заморозить лончер:
pm disable-user —user 0 com.google.android.tvlauncher
Разморозить лончер:
pm enable —user 0 com.google.android.tvlauncher
-----------------------------------
Удалить приложение:
pm uninstall имя пакета
pm uninstall com.termux
pm uninstall com.skype.raider
pm uninstall com.android.launcher
pm uninstall com.google.android.gsf.login
pm uninstall com.google.android.youtube.tv
pm uninstall com.facebook.katana
pm uninstall com.google.android.backuptransport

-----------------------------------
Удалить системное приложение:
Пример: Google Play Фильмы
pm uninstall -k —user 0 com.google.android.videos
pm uninstall -k —user 0  com.google.android.backuptransport
-----------------------------------
