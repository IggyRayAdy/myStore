<#import "parts/common.ftlh" as c>

<#include "parts/authentication.ftlh">

<@c.page>
    <div><h4>${name}
            <br>Ваш баланс - ${balance}
        </h4></div>

    <#if order??> <#--опционально-->
        <#list products as product, long>
            ${product.typeOfProduct}
            ${product.label} ${product.description}

            <#if product.discount!= 0>
                Стоимость: ${((100-product.discount)/100)*product.cost}
            <#else>
                Стоимость: ${product.cost}
            </#if>
            <br>Количество: ${long}
            <div>
                <#if product.fileName??>
                    <img src="/img/${product.fileName}">
                </#if>
            </div>
            <form method="post" action="/order/deleteProdFromOrder">
                <button type="submit">remove</button>
                <input type="hidden" name="id" value="${product.id}"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
            <br>
        </#list>
        <div>
            Суммарная стоимость - ${orderCost}
        </div>

        <form method="post" action="/order/buyOrder">
            <button type="submit">Buy</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>

    <#else>
        <br> Корзина пуста
    </#if>

    <form action="/">
        <input type="submit" value="Main page"/>
    </form>

</@c.page>


