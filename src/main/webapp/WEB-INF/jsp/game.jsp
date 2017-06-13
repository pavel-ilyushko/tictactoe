<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
    <head>
        <title>Game</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>

    <body>
        <p>${game}</p>

        <c:forEach items="${game.toMatrix()}" var="row" varStatus="rowIndex">
            <c:forEach items="${row}" var="player" varStatus="colIndex">
                <c:choose>
                    <c:when test="${empty player}">
                        <c:choose>
                            <c:when test="${game.inProgress()}">
                                <a href="javascript:;"
                                   class="turn"
                                   data-row="${rowIndex.index}"
                                   data-col="${colIndex.index}">--</a>
                            </c:when>
                            <c:otherwise>
                                --
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        ${player}
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <br/>
        </c:forEach>
        <br/>
        <c:if test='${not game.inProgress()}'>
            ${game.status}!
        </c:if>
    </body>
</html>

<script type="text/javascript">
    $(document).on('click', '.turn', function (e) {
        e.preventDefault();
        var turn = $(e.target);
        $.ajax({
            'url': '/turn',
            'type': 'POST',
            'data': {
                'game': ${game.id},
                'row': turn.data('row'),
                'col': turn.data('col')
            },
            'success': function () {
                window.location.reload();
            }
        });
    });
</script>


