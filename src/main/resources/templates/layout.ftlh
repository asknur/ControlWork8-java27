<#macro layout>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
        <title>JavaShareHub</title>
    </head>
    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/">JavaShareHub</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item"><a class="nav-link" href="/">Файлы</a></li>
                    <#if SPRING_SECURITY_CONTEXT?? && SPRING_SECURITY_CONTEXT.authentication??>
                        <#assign auth = SPRING_SECURITY_CONTEXT.authentication>
                        <#if auth.principal?has_content && auth.principal != "anonymousUser">
                            <li class="nav-item"><a class="nav-link" href="/profile">Профиль</a></li>
                            <li class="nav-item"><a class="nav-link" href="/files/upload">Загрузить</a></li>
                        </#if>
                    </#if>
                </ul>
                <div class="d-flex gap-2">
                    <#if SPRING_SECURITY_CONTEXT?? && SPRING_SECURITY_CONTEXT.authentication??>
                        <#assign auth = SPRING_SECURITY_CONTEXT.authentication>
                        <#if auth.principal?has_content && auth.principal != "anonymousUser">
                            <form action="/auth/logout" method="post">
                                <#if _csrf??>
                                    <input type="hidden"
                                           name="${(_csrf.parameterName)!'csrf-param-name'}"
                                           value="${(_csrf.token)!'csrf-token'}"/>
                                </#if>
                                <button type="submit" class="btn btn-outline-light btn-sm">Выйти</button>
                            </form>
                        <#else>
                            <a href="/auth/login" class="btn btn-outline-light btn-sm">Войти</a>
                            <a href="/auth/register" class="btn btn-success btn-sm">Регистрация</a>
                        </#if>
                    <#else>
                        <a href="/auth/login" class="btn btn-outline-light btn-sm">Войти</a>
                        <a href="/auth/register" class="btn btn-success btn-sm">Регистрация</a>
                    </#if>
                </div>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <#nested>
    </div>
    </body>
    </html>
</#macro>