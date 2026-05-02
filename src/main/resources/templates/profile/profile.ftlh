<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="row">
        <div class="col-md-3">
            <div class="card p-3 shadow-sm">
                <h5>${user.name}</h5>
                <p class="text-muted">${user.email}</p>
                <a href="/files/upload" class="btn btn-primary btn-sm">Загрузить файл</a>
            </div>
        </div>
        <div class="col-md-9">
            <h4>Мои файлы</h4>
            <#if files?size == 0>
                <div class="alert alert-info">Вы ещё не загрузили ни одного файла</div>
            <#else>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Название</th>
                        <th>Размер</th>
                        <th>Статус</th>
                        <th>Скачиваний</th>
                        <th>Дата</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list files as f>
                        <tr>
                            <td>${f.name}</td>
                            <td>${(f.size / 1024)?string["0.#"]} KB</td>
                            <td>
                                <#if f.isPublic?? && f.isPublic>
                                    <span class="badge bg-success">Публичный</span>
                                <#else>
                                    <span class="badge bg-secondary">Приватный</span>
                                </#if>
                            </td>
                            <td>${f.downloadCount}</td>
                            <td>${f.uploadedAt!}</td>
                            <td>
                                <#if f.isPublic?? && f.isPublic>
                                    <a href="/files/download/public/${f.id}" class="btn btn-sm btn-primary">Скачать</a>
                                <#else>
                                    <#if f.privateKey??>
                                        <a href="/files/download/private/${f.privateKey}"
                                           class="btn btn-sm btn-warning">Ссылка</a>
                                    <#else>
                                        <span class="text-muted">Ключ использован</span>
                                    </#if>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </#if>

            <nav>
                <ul class="pagination justify-content-center mt-3">
                    <li class="page-item <#if currentPage == 0>disabled</#if>">
                        <a class="page-link" href="?page=${currentPage - 1}">Назад</a>
                    </li>
                    <#list 0..(totalPages - 1) as p>
                        <li class="page-item <#if p == currentPage>active</#if>">
                            <a class="page-link" href="?page=${p}">${p + 1}</a>
                        </li>
                    </#list>
                    <li class="page-item <#if currentPage == totalPages - 1>disabled</#if>">
                        <a class="page-link" href="?page=${currentPage + 1}">Вперёд</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</@main.layout>