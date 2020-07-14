<#import "parts/common.ftlh" as c>

<#include "parts/authentication.ftlh">

<@c.page>
    <div><h4>${name}</h4></div>
    <br>
<#--    ${message2!}-->
    <#if isAdmin>
        <div>
            <form method="post" enctype="multipart/form-data">
                <div><input type="int" name="cost" <#--value="<#if product??>${product.cost}</#if>"--> placeholder="Cost"/>
                    <#if costError??>
                        <i>${costError}</i>
                    </#if>
                </div>
                <div><input type="int" name="discount" <#--value="<#if product??>${product.discount}</#if>"-->
                            placeholder="Discount"/>
                    <#if discountError??>
                        <i>${discountError}</i>
                    </#if>
                </div>
                <div><input type="text" name="label" value="<#if product??>${product.label}</#if>" placeholder="Label"/>
                    <#if labelError??>
                        <i>${labelError}</i>
                    </#if>
                </div>
                <div><input type="text" name="description" value="<#if product??>${product.description}</#if>"
                            placeholder="Description"/>
                    <#if descriptionError??>
                        <i>${descriptionError}</i>
                    </#if>
                </div>
                <div>
                    <input type="file" name="file">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <br>
                <form action="/product/${product.id}">
                    <input type="submit" value="Update"/>
                </form>
            </form>
        </div>
    </#if>

    <#if product??>
        ${product.typeOfProduct}
        <div>
            ${product.label} ${product.description}
            <#if product.discount!= 0>
                Стоимость: ${((100-product.discount)/100)*product.cost}
            <#else>
                Стоимость: ${product.cost}
            </#if>
        </div>
        <div>
            <#if product.fileName??>
                <img src="/img/${product.fileName}">
            </#if>
        </div>
    </#if>
    ${message!}

    <form action="/">
        <input type="submit" value="Main page"/>
    </form>

    <form method="post" action="/product/${product.id}/toCartProdPage">
        <button type="submit">addToCart</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>

    <#if isAdmin>
        <form method="post" action="/product/${product.id}/delete">
            <button type="submit">delete</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </#if>
</@c.page>


