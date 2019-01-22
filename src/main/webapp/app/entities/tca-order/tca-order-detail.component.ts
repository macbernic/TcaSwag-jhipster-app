import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITcaOrder } from 'app/shared/model/tca-order.model';

@Component({
    selector: 'jhi-tca-order-detail',
    templateUrl: './tca-order-detail.component.html'
})
export class TcaOrderDetailComponent implements OnInit {
    tcaOrder: ITcaOrder;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tcaOrder }) => {
            this.tcaOrder = tcaOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
