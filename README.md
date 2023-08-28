# Курсовой проект «Сервис перевода денег»

## Описание проекта

Необходимо разработать приложение — REST-сервис. Сервис должен предоставить интерфейс для перевода денег с одной карты на другую по заранее описанной спецификации.

Заранее подготовленное веб-приложение (FRONT) должно подключаться к разработанному сервису без доработок и использовать его функционал для перевода денег.

## Требования к приложению

- Сервис должен предоставлять REST-интерфейс для интеграции с FRONT.
- Сервис должен реализовывать все методы перевода с одной банковской карты на другую, описанные [в протоколе](https://github.com/netology-code/jd-homeworks/blob/master/diploma/MoneyTransferServiceSpecification.yaml).
- Все изменения должны записываться в файл — лог переводов в произвольном формате с указанием:
- даты;
- времени;
- карты, с которой было списание;
- карты зачисления;
- суммы;
- комиссии;
- результата операции, если был.

## Требования в реализации

- Приложение разработано с использованием Spring Boot.
- Использован сборщик пакетов gradle/maven.
- Для запуска используется Docker, Docker Compose .
- Код размещён на GitHub.
- Код покрыт юнит-тестами с использованием mockito.
- Добавлены интеграционные тесты с использованием testcontainers.

Шаги реализации:

1. Изучить протокол получения и отправки сообщений.
1. Нарисовать схему приложений.
1. Описать архитектуру приложения, где хранятся настройки, описать формат хранения данных о картах.
1. Создать репозиторий проекта на GitHub.
1. Протестировать приложение с помощью curl/postman.
1. Написать Dockerfile и создать контейнер.
1. Написать Docker Compose  скрипт для запуска FRONT и написанного REST-SERVICE.
1. Протестировать запуск с помощью Docker Compose и интеграцию с FRONT.
1. Написать README.md к проекту, где описать команду запуска, порт и примеры запросов.
1. Отправить на проверку.

## Описание интеграции с FRONT

FRONT доступен [по адресу](https://github.com/serp-ya/card-transfer). Можно скачать репозиторий и запустить Node.js приложение локально (в описании репозитория FRONT добавлена информация, как запустить) или использовать уже развёрнутое демо-приложение [по адресу](https://serp-ya.github.io/card-transfer/) (тогда ваш API должен быть запущен [по адресу](http://localhost:5500/)).
> Весь API FRONT был описан в соответствии [YAML](https://github.com/netology-code/jd-homeworks/blob/master/diploma/MoneyTransferServiceSpecification.yaml)
файла по спецификации OpenAPI (подробнее [по ссылке 1](https://swagger.io/specification/) и [ссылке 2](https://starkovden.github.io/introduction-openapi-and-swagger.html)).

При возникновении любых вопросов, пожалуйста, задавайте их преподавателю.

Успехов в разработке!

## Ошибки
### Frontend
```shell
npm ERR! code 1
npm ERR! path /Users/dromakin/Desktop/card-transfer/node_modules/node-sass
npm ERR! command failed
npm ERR! command sh -c node scripts/build.js
npm ERR! Building: /opt/homebrew/Cellar/node/20.5.1/bin/node /Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/bin/node-gyp.js rebuild --verbose --libsass_ext= --libsass_cflags= --libsass_ldflags= --libsass_library=
npm ERR! gyp info it worked if it ends with ok
npm ERR! gyp verb cli [
npm ERR! gyp verb cli   '/opt/homebrew/Cellar/node/20.5.1/bin/node',
npm ERR! gyp verb cli   '/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/bin/node-gyp.js',
npm ERR! gyp verb cli   'rebuild',
npm ERR! gyp verb cli   '--verbose',
npm ERR! gyp verb cli   '--libsass_ext=',
npm ERR! gyp verb cli   '--libsass_cflags=',
npm ERR! gyp verb cli   '--libsass_ldflags=',
npm ERR! gyp verb cli   '--libsass_library='
npm ERR! gyp verb cli ]
npm ERR! gyp info using node-gyp@3.8.0
npm ERR! gyp info using node@20.5.1 | darwin | arm64
npm ERR! gyp verb command rebuild []
npm ERR! gyp verb command clean []
npm ERR! gyp verb clean removing "build" directory
npm ERR! gyp verb command configure []
npm ERR! gyp verb check python checking for Python executable "python2" in the PATH
npm ERR! gyp verb `which` succeeded python2 /Users/dromakin/.pyenv/shims/python2
npm ERR! gyp verb check python version `/Users/dromakin/.pyenv/shims/python2 -c "import sys; print "2.7.18
npm ERR! gyp verb check python version .%s.%s" % sys.version_info[:3];"` returned: %j
npm ERR! gyp verb get node dir no --target version specified, falling back to host node version: 20.5.1
npm ERR! gyp verb command install [ '20.5.1' ]
npm ERR! gyp verb install input version string "20.5.1"
npm ERR! gyp verb install installing version: 20.5.1
npm ERR! gyp verb install --ensure was passed, so won't reinstall if already installed
npm ERR! gyp verb install version is already installed, need to check "installVersion"
npm ERR! gyp verb got "installVersion" 9
npm ERR! gyp verb needs "installVersion" 9
npm ERR! gyp verb install version is good
npm ERR! gyp verb get node dir target node version installed: 20.5.1
npm ERR! gyp verb build dir attempting to create "build" dir: /Users/dromakin/Desktop/card-transfer/node_modules/node-sass/build
npm ERR! gyp verb build dir "build" dir needed to be created? /Users/dromakin/Desktop/card-transfer/node_modules/node-sass/build
npm ERR! gyp verb build/config.gypi creating config file
npm ERR! gyp verb build/config.gypi writing out config file: /Users/dromakin/Desktop/card-transfer/node_modules/node-sass/build/config.gypi
npm ERR! gyp verb config.gypi checking for gypi file: /Users/dromakin/Desktop/card-transfer/node_modules/node-sass/config.gypi
npm ERR! gyp verb common.gypi checking for gypi file: /Users/dromakin/Desktop/card-transfer/node_modules/node-sass/common.gypi
npm ERR! gyp verb gyp gyp format was not specified; forcing "make"
npm ERR! gyp info spawn /Users/dromakin/.pyenv/shims/python2
npm ERR! gyp info spawn args [
npm ERR! gyp info spawn args   '/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/gyp/gyp_main.py',
npm ERR! gyp info spawn args   'binding.gyp',
npm ERR! gyp info spawn args   '-f',
npm ERR! gyp info spawn args   'make',
npm ERR! gyp info spawn args   '-I',
npm ERR! gyp info spawn args   '/Users/dromakin/Desktop/card-transfer/node_modules/node-sass/build/config.gypi',
npm ERR! gyp info spawn args   '-I',
npm ERR! gyp info spawn args   '/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/addon.gypi',
npm ERR! gyp info spawn args   '-I',
npm ERR! gyp info spawn args   '/Users/dromakin/.node-gyp/20.5.1/include/node/common.gypi',
npm ERR! gyp info spawn args   '-Dlibrary=shared_library',
npm ERR! gyp info spawn args   '-Dvisibility=default',
npm ERR! gyp info spawn args   '-Dnode_root_dir=/Users/dromakin/.node-gyp/20.5.1',
npm ERR! gyp info spawn args   '-Dnode_gyp_dir=/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp',
npm ERR! gyp info spawn args   '-Dnode_lib_file=/Users/dromakin/.node-gyp/20.5.1/<(target_arch)/node.lib',
npm ERR! gyp info spawn args   '-Dmodule_root_dir=/Users/dromakin/Desktop/card-transfer/node_modules/node-sass',
npm ERR! gyp info spawn args   '-Dnode_engine=v8',
npm ERR! gyp info spawn args   '--depth=.',
npm ERR! gyp info spawn args   '--no-parallel',
npm ERR! gyp info spawn args   '--generator-output',
npm ERR! gyp info spawn args   'build',
npm ERR! gyp info spawn args   '-Goutput_dir=.'
npm ERR! gyp info spawn args ]
npm ERR! Traceback (most recent call last):
npm ERR!   File "/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/gyp/gyp_main.py", line 16, in <module>
npm ERR!     sys.exit(gyp.script_main())
npm ERR!   File "/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/gyp/pylib/gyp/__init__.py", line 545, in script_main
npm ERR!     return main(sys.argv[1:])
npm ERR!   File "/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/gyp/pylib/gyp/__init__.py", line 538, in main
npm ERR!     return gyp_main(args)
npm ERR!   File "/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/gyp/pylib/gyp/__init__.py", line 514, in gyp_main
npm ERR!     options.duplicate_basename_check)
npm ERR!   File "/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/gyp/pylib/gyp/__init__.py", line 130, in Load
npm ERR!     params['parallel'], params['root_targets'])
npm ERR!   File "/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/gyp/pylib/gyp/input.py", line 2783, in Load
npm ERR!     variables, includes, depth, check, True)
npm ERR!   File "/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/gyp/pylib/gyp/input.py", line 399, in LoadTargetBuildFile
npm ERR!     includes, True, check)
npm ERR!   File "/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/gyp/pylib/gyp/input.py", line 271, in LoadOneBuildFile
npm ERR!     aux_data, includes, check)
npm ERR!   File "/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/gyp/pylib/gyp/input.py", line 308, in LoadBuildFileIncludesIntoDict
npm ERR!     LoadOneBuildFile(include, data, aux_data, None, False, check),
npm ERR!   File "/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/gyp/pylib/gyp/input.py", line 251, in LoadOneBuildFile
npm ERR!     None)
npm ERR!   File "/Users/dromakin/.node-gyp/20.5.1/include/node/common.gypi", line 1
npm ERR!     nerate ',
npm ERR!             ^
npm ERR! SyntaxError: EOL while scanning string literal
npm ERR! gyp ERR! configure error 
npm ERR! gyp ERR! stack Error: `gyp` failed with exit code: 1
npm ERR! gyp ERR! stack     at ChildProcess.onCpExit (/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/lib/configure.js:345:16)
npm ERR! gyp ERR! stack     at ChildProcess.emit (node:events:514:28)
npm ERR! gyp ERR! stack     at ChildProcess._handle.onexit (node:internal/child_process:294:12)
npm ERR! gyp ERR! System Darwin 21.6.0
npm ERR! gyp ERR! command "/opt/homebrew/Cellar/node/20.5.1/bin/node" "/Users/dromakin/Desktop/card-transfer/node_modules/node-gyp/bin/node-gyp.js" "rebuild" "--verbose" "--libsass_ext=" "--libsass_cflags=" "--libsass_ldflags=" "--libsass_library="
npm ERR! gyp ERR! cwd /Users/dromakin/Desktop/card-transfer/node_modules/node-sass
npm ERR! gyp ERR! node -v v20.5.1
npm ERR! gyp ERR! node-gyp -v v3.8.0
npm ERR! gyp ERR! not ok 
npm ERR! Build failed with error code: 1

npm ERR! A complete log of this run can be found in: /Users/dromakin/.npm/_logs/2023-08-28T22_22_25_346Z-debug-0.log
```

Попытался запустить через docker-compose, не помогло, ошибка такая же.

