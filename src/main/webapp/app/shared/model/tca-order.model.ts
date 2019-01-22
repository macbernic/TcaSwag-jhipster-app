import { Moment } from 'moment';
import { IProductSku } from 'app/shared/model//product-sku.model';
import { ITcaMember } from 'app/shared/model//tca-member.model';

export const enum OrderStatus {
    DRAFT = 'DRAFT',
    SUBMITTED = 'SUBMITTED',
    PAYED = 'PAYED',
    DELIVERED = 'DELIVERED'
}

export interface ITcaOrder {
    id?: number;
    orderId?: number;
    status?: OrderStatus;
    date?: Moment;
    first?: boolean;
    amount?: number;
    productSkuses?: IProductSku[];
    tcaMember?: ITcaMember;
}

export class TcaOrder implements ITcaOrder {
    constructor(
        public id?: number,
        public orderId?: number,
        public status?: OrderStatus,
        public date?: Moment,
        public first?: boolean,
        public amount?: number,
        public productSkuses?: IProductSku[],
        public tcaMember?: ITcaMember
    ) {
        this.first = this.first || false;
    }
}
