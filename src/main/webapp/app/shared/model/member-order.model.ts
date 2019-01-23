import { IMemberOrderItem } from 'app/shared/model//member-order-item.model';
import { IMember } from 'app/shared/model//member.model';

export const enum OrderStatus {
    DRAFT = 'DRAFT',
    SUBMITTED = 'SUBMITTED',
    PAYED = 'PAYED',
    DELIVERED = 'DELIVERED',
    ARCHIVED = 'ARCHIVED'
}

export interface IMemberOrder {
    id?: number;
    orderId?: string;
    status?: OrderStatus;
    first?: boolean;
    amount?: number;
    items?: IMemberOrderItem[];
    tcaMember?: IMember;
}

export class MemberOrder implements IMemberOrder {
    constructor(
        public id?: number,
        public orderId?: string,
        public status?: OrderStatus,
        public first?: boolean,
        public amount?: number,
        public items?: IMemberOrderItem[],
        public tcaMember?: IMember
    ) {
        this.first = this.first || false;
    }
}
