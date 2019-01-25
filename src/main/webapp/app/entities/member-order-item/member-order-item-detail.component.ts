import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMemberOrderItem } from 'app/shared/model/member-order-item.model';

@Component({
    selector: 'jhi-member-order-item-detail',
    templateUrl: './member-order-item-detail.component.html'
})
export class MemberOrderItemDetailComponent implements OnInit {
    memberOrderItem: IMemberOrderItem;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ memberOrderItem }) => {
            this.memberOrderItem = memberOrderItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
