import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITcaMember } from 'app/shared/model/tca-member.model';

@Component({
    selector: 'jhi-tca-member-detail',
    templateUrl: './tca-member-detail.component.html'
})
export class TcaMemberDetailComponent implements OnInit {
    tcaMember: ITcaMember;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tcaMember }) => {
            this.tcaMember = tcaMember;
        });
    }

    previousState() {
        window.history.back();
    }
}
