<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<aside class="main-sidebar">

    <section class="sidebar">
        <div class="user-panel">
            <div class="pull-left image">
                <img src="/adminLTE/dist/img/default-user-image.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>${userMap.name }</p>
                <%-- Status --%>
                <a href="/logout"><i class="fa fa-circle text-success"></i> logout</a>
            </div>
        </div>

        <ul class="sidebar-menu" data-widget="tree">
            <li class="header">메뉴</li>
            <li class="treeview active">
                <a href="javascript:void(0);">
                    <i class="fa fa-clipboard"></i>
                    <span>게시판</span>
                    <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="javascript:void(0);" id="writeBtn2"><i class="fa fa-edit"></i> <span> 글쓰기</span></a></li>
                    <li><a href="javascript:void(0);" onclick="goBoardList(this);" data-value="1" data-type="main"><i class="fa fa-list"></i> <span> 목록</span></a></li>
                </ul>
            </li>
        </ul>
    </section>
</aside>