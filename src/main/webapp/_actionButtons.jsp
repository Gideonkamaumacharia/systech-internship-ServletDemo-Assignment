<%-- _actionButtons.jsp --%>
<td>
    <a href="${editUrl}?id=${item.id}">
        <button>Edit</button>
    </a>

    <form action="${deleteUrl}" method="post" style="display:inline;">
        <input type="hidden" name="id" value="${item.id}" />
        <button type="submit">Delete</button>
    </form>
</td>