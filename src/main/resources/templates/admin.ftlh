<#import "./layout.ftlh" as main>
<@main.layout>
    <h2 class="mb-4">Панель администратора</h2>
    <h4>Все файлы</h4>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Название</th>
            <th>Статус</th>
            <th>Скачиваний</th>
            <th>Владелец</th>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody>
        <#list files as f>
            <tr>
                <td>${f.name}</td>
                <td>
                    <#if f.isPublic?? && f.isPublic>
                        <span class="badge bg-success">Публичный</span>
                    <#else>
                        <span class="badge bg-secondary">Приватный</span>
                    </#if>
                </td>
                <td>${f.downloadCount}</td>
                <td>${f.user.email}</td>
                <td>
                    <form action="/admin/files/delete/${f.id}" method="post">
                        <#if _csrf??>
                            <input type="hidden"
                                   name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!''}"/>
                        </#if>
                        <button type="submit" class="btn btn-sm btn-danger"
                                onclick="return confirm('Удалить файл?')">
                            Удалить
                        </button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@main.layout>