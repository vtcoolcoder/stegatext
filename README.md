# stegatext
Программа для стеганографии текста в текст.

                                        Примеры использования

Обфускация:
    java --enable-preview stegatext.StegaTextRunner {--encode|-e} < source-txt-file > obfs-txt-file

Деобфускация:
    java --enable-preview stegatext.StegaTextRunner {--decode|-d} < obfs-txt-file > decoded-source-txt-file

Шифрование:
    java --enable-preview stegatext.StegaTextRunner {--encrypt|-E} < source-txt-file > encrypted-txt-file

Дешифрование:
    java --enable-preview stegatext.StegaTextRunner {--decrypt|-D} < encrypted-txt-file > decrypted-source-txt-file

Справка:
    java --enable-preview stegatext.StegaTextRunner {--help|-h}

