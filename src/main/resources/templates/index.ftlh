<#import "layout.ftlh" as main>
<@main.layout>
    <h2 class="mb-4">Публичные файлы</h2>

    <#if files?size == 0>
        <div class="alert alert-info">Файлов пока нет</div>
    <#else>
        <div class="row">
            <#list files as f>
                <div class="col-md-4 mb-3">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">${f.name}</h5>
                            <p class="text-muted">Размер: ${(f.size / 1024)?string["0.#"]} KB</p>
                            <p class="text-muted">Скачиваний: ${f.downloadCount}</p>
                            <#if f.uploadedAt??>
                                <small class="text-muted">${f.uploadedAt}</small>
                            </#if>
                        </div>
                        <div class="card-footer">
                            <a href="/files/download/public/${f.id}" class="btn btn-primary btn-sm">Скачать</a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
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
</@main.layout>