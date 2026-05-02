<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow p-4">
                <h3 class="text-center mb-4">Вход</h3>
                <#if RequestParameters.error??>
                    <div class="alert alert-danger">Неверный email или пароль</div>
                </#if>
                <form action="/auth/login" method="post">
                    <#if _csrf??>
                        <input
                                type="hidden"
                                name="${(_csrf.parameterName)!'csrf-param-name'}"
                                value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" name="username" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Пароль</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Войти</button>
                </form>
                <div class="text-center mt-3">
                    <a href="/auth/register">Нет аккаунта? Регистрация</a>
                </div>
            </div>
        </div>
    </div>
</@main.layout>