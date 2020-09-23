package sschr15.qol.interfaces;

import sschr15.qol.code.Ref;

import java.util.List;

public interface IGameData {
    List<Ref.ConflictingResource> getConflictingResourceList();
}
