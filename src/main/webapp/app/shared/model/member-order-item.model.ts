import { IMemberOrder } from 'app/shared/model//member-order.model';
import { IProduct } from 'app/shared/model//product.model';

export interface IMemberOrderItem {
    id?: number;
    customText?: string;
    appliedPrice?: number;
    memberOrder?: IMemberOrder;
    sku?: IProduct;
}

export class MemberOrderItem implements IMemberOrderItem {
    constructor(
        public id?: number,
        public customText?: string,
        public appliedPrice?: number,
        public memberOrder?: IMemberOrder,
        public sku?: IProduct
    ) {}
}
