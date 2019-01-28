import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMemberOrder } from 'app/shared/model/member-order.model';

@Component({
    selector: 'jhi-member-order-detail',
    templateUrl: './member-order-detail.component.html'
})
export class MemberOrderDetailComponent implements OnInit {
    memberOrder: IMemberOrder;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ memberOrder }) => {
            this.memberOrder = memberOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
