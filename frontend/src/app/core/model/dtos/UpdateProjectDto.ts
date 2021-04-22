import {Project} from "../Project";

export class UpdateProjectDto {
  public id?: number;
  public name?: string;
  public icon?: string;
  public description?: string;
  public git?: string;
  public color?: string;

  static fromProject(project: Project): UpdateProjectDto{
    return new UpdateProjectDto(
      project.id,
      project.name,
      project.icon,
      project.description,
      project.git,
      project.color
    );
  }

  constructor(id: number,
              name: string,
              icon: string,
              description: string,
              git: string,
              color: string
  ) {
    this.id = id;
    this.name = name;
    this.icon = icon;
    this.description = description;
    this.git = git;
    this.color = color;
  }
}
