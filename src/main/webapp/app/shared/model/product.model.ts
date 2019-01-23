import { IProductAsset } from 'app/shared/model//product-asset.model';
import { IMemberOrderItem } from 'app/shared/model//member-order-item.model';

export const enum SportType {
    RUNNING = 'RUNNING',
    SWIMMING = 'SWIMMING',
    CYCLING = 'CYCLING',
    TRIATHLON = 'TRIATHLON',
    SWIMRUN = 'SWIMRUN'
}

export const enum ProductSize {
    UNIQUE = 'UNIQUE',
    XXSMALL = 'XXSMALL',
    XSMALL = 'XSMALL',
    SMALL = 'SMALL',
    MEDIUM = 'MEDIUM',
    LARGE = 'LARGE',
    XLARGE = 'XLARGE',
    XXLARGE = 'XXLARGE'
}

export const enum GenderType {
    MALE = 'MALE',
    FEMALE = 'FEMALE',
    UNISEX = 'UNISEX'
}

export interface IProduct {
    id?: number;
    productId?: string;
    title?: string;
    desc?: any;
    brand?: string;
    sport?: SportType;
    size?: ProductSize;
    gender?: GenderType;
    retailPrice?: number;
    membersFirstPrice?: number;
    membersPrice?: number;
    assets?: IProductAsset[];
    orderedItems?: IMemberOrderItem[];
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public productId?: string,
        public title?: string,
        public desc?: any,
        public brand?: string,
        public sport?: SportType,
        public size?: ProductSize,
        public gender?: GenderType,
        public retailPrice?: number,
        public membersFirstPrice?: number,
        public membersPrice?: number,
        public assets?: IProductAsset[],
        public orderedItems?: IMemberOrderItem[]
    ) {}
}
