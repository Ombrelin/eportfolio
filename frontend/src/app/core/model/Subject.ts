import {Ability} from "./Ability";

export class Subject {
  constructor(
    public id?: number,
    public name?: string,
    public abilities?: Array<Ability>) {
  }
}
