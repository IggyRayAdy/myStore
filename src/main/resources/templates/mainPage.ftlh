<#import "parts/common.ftlh" as c>

<#include "parts/authentication.ftlh">

<@c.page>

    <h4>${name}</h4>
    <form action="/order">
        <input type="submit" value="Order"/>
    </form>
    <#if isAdmin || isUser>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="submit" value="Logout"/>
        </form>
    </#if>
    <#if isAdmin>
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
            <#list types as type>
                <label><input type="checkbox" name="${type}"> ${type}  </label>
            </#list>
            <input type="file" name="file"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <br>
            <button type="submit">Add product</button>
        </form>
    </#if>

    <div>
        <br>
        <form method="post" action="/searchByLabel">
            <input type="searchTag" name="searchTag" placeholder="searchTag"/>
            <button type="submit">find</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
        <h4>${message!}</h4>
    </div>

    <div>
        <#if products??>
            <#list products as product>
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

                <form action="/product/${product.id}">
                    <button type="submit"> about</button>
                </form>

                <form method="post" action="/toCartMainPage">
                    <button type="submit">addToCart</button>
                    <input type="hidden" name="id" value="${product.id}"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
                </br>
            <#else>
                Products not exist
            </#list>
        </#if>
    </div>
</@c.page>


