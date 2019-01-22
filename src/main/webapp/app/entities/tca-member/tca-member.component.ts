import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITcaMember } from 'app/shared/model/tca-member.model';
import { AccountService } from 'app/core';
import { TcaMemberService } from './tca-member.service';

@Component({
    selector: 'jhi-tca-member',
    templateUrl: './tca-member.component.html'
})
export class TcaMemberComponent implements OnInit, OnDestroy {
    tcaMembers: ITcaMember[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected tcaMemberService: TcaMemberService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.tcaMemberService.query().subscribe(
            (res: HttpResponse<ITcaMember[]>) => {
                this.tcaMembers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTcaMembers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITcaMember) {
        return item.id;
    }

    registerChangeInTcaMembers() {
        this.eventSubscriber = this.eventManager.subscribe('tcaMemberListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
