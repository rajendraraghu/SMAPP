import { Moment } from 'moment';

export interface ISnowDDL {
  id?: number;
  name?: string;
  description?: string;
  sourcePath?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  sourceConnectionName?: string;
  sourceConnectionId?: number;
  snowflakeConnectionName?: string;
  snowflakeConnectionId?: number;
}

export class SnowDDL implements ISnowDDL {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public sourcePath?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public sourceConnectionName?: string,
    public sourceConnectionId?: number,
    public snowflakeConnectionName?: string,
    public snowflakeConnectionId?: number
  ) {}
}
