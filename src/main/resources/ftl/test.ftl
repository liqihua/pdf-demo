<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"></meta>
    <style>
        html,body{
            font-family: SimSun, Arial, Helvetica, sans-serif;
            font-size: 14px;
            color: #333;
        }
        .text-center{text-align: center;}
        .to-left{float: right;margin-right: 20px;}
        .clear{clear: both;}

    </style>
</head>
<body>
<div class="text-center"><h1>楼小二办公便利解决方案服务单</h1></div>
<div class="text-center"><h4>账号信息</h4></div>
<div>
    <p>
        系统已自动为您生成账号，用于登录楼小二企业服务中心 。登录后可进行密码修改、服务内容支付及选购其他商品/服务。
    </p>
</div>
<div>
    <span class="to-left">密码：${password}</span>
    <span class="to-left">账号：${username}</span>
    <span class="clear">&nbsp;</span>
</div>
<div class="text-center"><h4>服务内容</h4></div>
<div>
    <p>
        以下内容，系根据与您沟通确认的内容生成，请再次确认服务内容。确认无误后，相应服务内容将推送至您的楼小二账号下。请登录楼小二企业服务中心(www.kx2.com 完成支付。
    </p>
</div>

<#list serviceList as srv>
<div class="text-center"><h4>${srv.serviceName}</h4></div>
<table>
    <tr>
        <th class="text-center" width="70%">套餐内容</th>
        <th class="text-center" width="30%">金额</th>
    </tr>
    <tr>
        <td class="text-center">
            <p>${srv.productContent}</p>
        </td>
        <td class="text-center">${srv.amount}元</td>
    </tr>
</table>
</#list>




<div class="text-center"><h4>服务总价</h4></div>
<div class="text-center"><h2>${total}元</h2></div>
<div>
    <p>
        以上价格为服务的确认报价，您可放心按照系统为您生成订单进行支付，如有任何问题，请与楼小二客服人员沟通。
    </p>
</div>
</body>
</html>