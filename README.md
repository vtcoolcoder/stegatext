# stegatext
Программа для стеганографии текста в текст.

Требуется минимум 21 версия Java.

Если установлена 21 версия, в команде запуска после слова java добавить ключ --enable-preview.

Запись вида 
    {--<longParam>|-<shortParam>} означает: 

требуется обязательный параметр 
    --<longParam>, либо -<shortParam>.

                                            Примеры использования

Обфускация:

    java stegatext.StegaTextRunner {--encode|-e} < source-txt-file > obfs-txt-file

Деобфускация:

    java stegatext.StegaTextRunner {--decode|-d} < obfs-txt-file > decoded-source-txt-file

Шифрование:

    java stegatext.StegaTextRunner {--encrypt|-E} < source-txt-file > encrypted-txt-file

Дешифрование:

    java stegatext.StegaTextRunner {--decrypt|-D} < encrypted-txt-file > decrypted-source-txt-file
Справка:

    java stegatext.StegaTextRunner {--help|-h}

