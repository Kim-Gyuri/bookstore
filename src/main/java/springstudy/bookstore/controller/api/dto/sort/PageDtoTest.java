package springstudy.bookstore.controller.api.dto.sort;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import springstudy.bookstore.domain.enums.ItemSellStatus;
import springstudy.bookstore.domain.enums.OrderStatus;

@Data
public class PageDtoTest {
    private final int ITEMS_PER_PAGE = 4; // 페이지당 몇개 표시할건지
    private int pageSize; // 페이지당 몇개 표시할건지
    private int startPage;
    private int endPage;
    private int curPage;
    private boolean prev, next;

    private String sortParam;
    private ItemSellStatus sellStatus;
    private OrderStatus orderStatus;
    private long total;

    @Builder
    public PageDtoTest(long total, Pageable pageable) {
        this.total = total;
        this.curPage = pageable.getPageNumber();
        this.pageSize = ITEMS_PER_PAGE;

        // 페이지 계산
        this.startPage = (curPage / ITEMS_PER_PAGE) * ITEMS_PER_PAGE + 1;
        this.endPage = this.startPage + ITEMS_PER_PAGE - 1;

        int realEnd = (int) (Math.ceil((total * 1.0) / pageSize));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = startPage > 1;
        this.next = endPage < realEnd;
    }

    @Builder
    public PageDtoTest(long total, String sortParam, Pageable pageable) {
        this.total = total;
        this.sortParam = sortParam;
        this.curPage = pageable.getPageNumber();
        this.pageSize = ITEMS_PER_PAGE;

        // 페이지 계산
        this.startPage = (curPage / ITEMS_PER_PAGE) * ITEMS_PER_PAGE + 1;
        this.endPage = this.startPage + ITEMS_PER_PAGE - 1;

        int realEnd = (int) (Math.ceil((total * 1.0) / pageSize));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = startPage > 1;
        this.next = endPage < realEnd;
    }

    @Builder
    public PageDtoTest(long total, ItemSellStatus status, Pageable pageable) {
        this.total = total;
        this.sellStatus = status;
        this.curPage = pageable.getPageNumber();
        this.pageSize = ITEMS_PER_PAGE;

        // 페이지 계산
        this.startPage = (curPage / ITEMS_PER_PAGE) * ITEMS_PER_PAGE + 1;
        this.endPage = this.startPage + ITEMS_PER_PAGE - 1;

        int realEnd = (int) (Math.ceil((total * 1.0) / pageSize));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = startPage > 1;
        this.next = endPage < realEnd;
    }

    @Builder
    public PageDtoTest(long total, OrderStatus status, Pageable pageable) {
        this.total = total;
        this.orderStatus = status;
        this.curPage = pageable.getPageNumber();
        this.pageSize = ITEMS_PER_PAGE;

        // 페이지 계산
        this.startPage = (curPage / ITEMS_PER_PAGE) * ITEMS_PER_PAGE + 1;
        this.endPage = this.startPage + ITEMS_PER_PAGE - 1;

        int realEnd = (int) (Math.ceil((total * 1.0) / pageSize));

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        this.prev = startPage > 1;
        this.next = endPage < realEnd;
    }

    public boolean hasPrevious() {
        return prev;
    }

    public boolean hasNext() {
        return next;
    }

    public long totalPages() {
        return total;
    }

    public int pageSize() {
        return pageSize;
    }
}
