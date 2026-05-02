<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow p-4">
                <h3 class="mb-4">Загрузить файл</h3>
                <form action="/files/upload" method="post" enctype="multipart/form-data">
                    <#if _csrf??>
                        <input
                                type="hidden"
                                name="${(_csrf.parameterName)!'csrf-param-name'}"
                                value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>
                    <div class="mb-3">
                        <label class="form-label">Выберите файл</label>
                        <input type="file" name="file" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Статус</label>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="isPublic"
                                   id="public" value="true" checked>
                            <label class="form-check-label" for="public">Публичный</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="isPublic"
                                   id="private" value="false">
                            <label class="form-check-label" for="private">Приватный</label>
                        </div>
                    </div>
                    <div class="d-flex gap-2">
                        <a href="/profile" class="btn btn-outline-secondary">Отмена</a>
                        <button type="submit" class="btn btn-primary">Загрузить</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</@main.layout>